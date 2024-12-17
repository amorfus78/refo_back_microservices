package com.renfobackend.student.services;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.discovery.EurekaClient;
import com.renfobackend.student.dtos.SchoolDto;
import com.renfobackend.student.dtos.StudentDto;
import com.renfobackend.student.dtos.StudentWithSchoolDto;
import com.renfobackend.student.entities.Student;
import com.renfobackend.student.http.SchoolService;
import com.renfobackend.student.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private EurekaClient discoveryClient;

	@Autowired
	private StudentRepository studentRepository;

	public Student createStudent(StudentDto studentDto) throws Exception {
		Boolean schoolExists = doesSchoolExist(studentDto.getSchoolId());

		if (!schoolExists) {
			throw new Exception("School not found");
		}

		Student student = new Student();
		student.setName(studentDto.getName());
		student.setLastName(studentDto.getLastName());
		student.setSchoolId(studentDto.getSchoolId());

		return studentRepository.save(student);
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public StudentWithSchoolDto getStudentById(String id) throws Exception {
		Optional<Student> student = studentRepository.findById(id);

		if (!student.isPresent()) {
			throw new Exception("Student not found");
		}

		Long schoolId = student.get().getSchoolId();
		System.out.println("School ID: " + schoolId);
		SchoolDto school = getSchoolDetails(schoolId);

		StudentWithSchoolDto studentWithSchool = new StudentWithSchoolDto();
		studentWithSchool.setName(student.get().getName());
		studentWithSchool.setLastName(student.get().getLastName());
		studentWithSchool.setSchool(school);

		return studentWithSchool;
	}

	private Boolean doesSchoolExist(Long schoolId) {
		SchoolService schoolService = new SchoolService();
		return schoolService.getSchoolDetails(schoolId, discoveryClient) != null;
	}

	private SchoolDto getSchoolDetails(Long schoolId) {
		SchoolService schoolService = new SchoolService();
		return schoolService.getSchoolDetails(schoolId, discoveryClient);
	}
}
