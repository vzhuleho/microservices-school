package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SchoolClass {

	private static final int FIRST_GRADE = 1;

	@Id
	private Long id;

	@Column(nullable = false)
	private int grade;

	@Column(nullable = false)
	private String letter;

	@Column(nullable = false)
	private int year;

	@OneToMany(mappedBy = "schoolClass")
	private List<Pupil> pupils = new ArrayList<>();

	@OneToOne(mappedBy = "schoolClass")
	private Schedule schedule;
}
