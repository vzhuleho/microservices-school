package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SchoolClass {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = false)
	private int grade;

	@Column(nullable = false)
	private String letter;

	@Column(nullable = false)
	private int foundationYear;
}
