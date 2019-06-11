package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Lesson {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = false)
	@NonNull
	private LocalDate date;

	@Column(nullable = false)
	private final int index;

	@ManyToOne
	@JoinColumn
	private Subject subject;

	@ManyToOne
	@JoinColumn
	private Teacher teacher;

	@ManyToOne
	@JoinColumn
	@NonNull
	private SchoolClass schoolClass;

	@Column
	private String note;

	@OneToMany
	@JoinColumn
	private List<Absence> absences = new ArrayList<>();

	@OneToMany
	@JoinColumn
	private List<Mark> marks = new ArrayList<>();
}
