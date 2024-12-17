package com.renfobackend.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renfobackend.student.dtos.StudentDto;
import com.renfobackend.student.dtos.StudentWithSchoolDto;
import com.renfobackend.student.entities.Student;
import com.renfobackend.student.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping
	public Student createStudent(@RequestBody StudentDto studentDto) throws Exception {
		return studentService.createStudent(studentDto);
	}

	@GetMapping("/{id}")
	public StudentWithSchoolDto getStudentById(@PathVariable String id) throws Exception {
		return studentService.getStudentById(id);
	}

	@GetMapping
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}
}

