package com.renfobackend.student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.renfobackend.student.entities.Student;

public interface StudentRepository extends MongoRepository<Student, String> {

}
