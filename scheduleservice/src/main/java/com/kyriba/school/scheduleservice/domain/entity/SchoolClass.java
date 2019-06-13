package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SchoolClass {

	private static final int FIRST_GRADE = 1;

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

	@OneToMany(mappedBy = "schoolClass")
	private Set<Pupil> pupils;

	@OneToOne(mappedBy = "schoolClass")
	private Schedule schedule;


	public static int currentToFoundationYear(int currentYear, int grade) {
		return grade == FIRST_GRADE ? currentYear : currentYear - grade;
	}
}
