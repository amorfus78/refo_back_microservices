package com.renfobackend.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.renfobackend.auth.dtos.AuthenticatedUserDto;
import com.renfobackend.auth.dtos.LoginDto;
import com.renfobackend.auth.entities.User;
import com.renfobackend.auth.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {
	final private String secretKey = "aRandomVerySecureKeyThatShouldBeStoredInASecureManner";
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public AuthenticatedUserDto login(LoginDto loginDto) {
		User user = userRepository.findByUsername(loginDto.getUsername());
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		if (!verifyPassword(loginDto.getPassword(), user.getPasswordHash())) {
			throw new RuntimeException("Invalid password");
		}
		return new AuthenticatedUserDto(user.getId(), user.getUsername(), generateToken(user));
	}

	public User register(LoginDto loginDto) {
		User user = new User();
		user.setUsername(loginDto.getUsername());
		user.setPasswordHash(hashPassword(loginDto.getPassword()));
		return userRepository.save(user);
	}

	private String generateToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();
	}

	private String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	private boolean verifyPassword(String password, String hashedPassword) {
		return passwordEncoder.matches(password, hashedPassword);
	}
}
