package com.renfobackend.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renfobackend.auth.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
