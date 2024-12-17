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
public class SchoolDto {

	private Long id;
	private String name;
	private String address;
	private String city;
	private String director;
}
