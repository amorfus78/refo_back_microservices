package com.renfobackend.gateway;

import java.security.Key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("students", r -> r
				.path("/student/**")
				.filters(f -> {
					return f.stripPrefix(1).filter(jwtAuthenticationFilter());
				})
				.uri("lb://STUDENT")
			)
			.route("school", r -> r
				.path("/schools/**")
				.filters(f -> {
					return f.stripPrefix(1).filter(jwtAuthenticationFilter());
				})
				.uri("lb://SCHOOL")
			)
			.route("auth", r -> r
				.path("/auth/**")
				.filters(f -> {
					return f.stripPrefix(1);
				})
				.uri("lb://AUTH")
			)
			.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	private GatewayFilter jwtAuthenticationFilter() {
		return (exchange, chain) -> {
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				if (validateToken(token)) {
					return chain.filter(exchange);
				}
			}
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		};
	}
	
	private boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
			return false;
		}
	}

	final private String secretKey = "aRandomVerySecureKeyThatShouldBeStoredInASecureManner";
	final private Key jwtKey = Keys.hmacShaKeyFor(secretKey.getBytes());
}
