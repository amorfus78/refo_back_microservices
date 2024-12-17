package com.renfobackend.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renfobackend.school.entities.School;
import com.renfobackend.school.dtos.SchoolDto;
import com.renfobackend.school.repositories.SchoolRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SchoolService {
	@Autowired
	private SchoolRepository schoolRepository;

	public List<School> getAllSchools() {
		return schoolRepository.findAll();
	}

	public School getSchoolById(Long id) {
		return schoolRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));
	}

	public School createSchool(SchoolDto schoolDto) {
		School school = new School();
		school.setName(schoolDto.getName());
		school.setAddress(schoolDto.getAddress());
		school.setCity(schoolDto.getCity());
		school.setDirector(schoolDto.getDirector());
		return schoolRepository.save(school);
	}	
}

