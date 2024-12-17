package com.renfobackend.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renfobackend.school.entities.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
