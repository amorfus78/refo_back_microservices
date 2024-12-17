package com.renfobackend.school.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.renfobackend.school.entities.School;
import com.renfobackend.school.services.SchoolService;
import com.renfobackend.school.dtos.SchoolDto;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/schools")
public class SchoolController {

	private final SchoolService schoolService;

	public SchoolController(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@GetMapping
	public List<School> getAllSchools() {
		return schoolService.getAllSchools();
	}

	@GetMapping("/{id}")
	public School getSchoolById(@PathVariable Long id) {
		return schoolService.getSchoolById(id);
	}

	@PostMapping
	public School createSchool(@RequestBody SchoolDto schoolDto) {
		return schoolService.createSchool(schoolDto);
	}
}

