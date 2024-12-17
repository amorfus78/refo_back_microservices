package com.renfobackend.student.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithSchoolDto {

	private String name;
	private String lastName;
	private SchoolDto school;
}