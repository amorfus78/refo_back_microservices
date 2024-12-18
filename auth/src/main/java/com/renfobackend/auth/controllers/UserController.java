package com.renfobackend.auth.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.renfobackend.auth.dtos.AuthenticatedUserDto;
import com.renfobackend.auth.dtos.LoginDto;
import com.renfobackend.auth.entities.User;
import com.renfobackend.auth.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticatedUserDto> login(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(userService.login(loginDto));
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(userService.register(loginDto));
	}
}
