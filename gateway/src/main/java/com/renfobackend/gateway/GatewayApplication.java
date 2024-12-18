package com.renfobackend.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("students", r -> r
				.path("/student/**")
				.filters(f -> {
					return f.stripPrefix(1);
				})
				.uri("lb://STUDENT")
			)
			.route("school", r -> r
				.path("/schools/**")
				.filters(f -> {
					return f.stripPrefix(1);
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
}
