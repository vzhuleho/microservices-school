package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Lesson {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private int index;

	@ManyToOne
	@JoinColumn
	private Subject subject;

	@ManyToOne
	@JoinColumn
	private Teacher teacher;

	@ManyToOne
	@JoinColumn
	private SchoolClass schoolClass;

	@Column
	private String note;

	@OneToMany
	@JoinColumn
	private List<Absence> absences = new ArrayList<>();

	@OneToMany
	@JoinColumn
	private List<Mark> marks = new ArrayList<>();

	public Lesson(LocalDate date, int index, SchoolClass schoolClass) {
		this.date = date;
		this.index = index;
		this.schoolClass = schoolClass;
	}
}
