package com.kyriba.school.scheduleservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Schedule {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = false)
	private int year;

	@OneToOne
	@JoinColumn(nullable = false)
	private SchoolClass schoolClass;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	@JsonBackReference
	private List<Day> days = new ArrayList<>();

	public Schedule(int year, @NonNull SchoolClass schoolClass) {
		this.year = year;
		this.schoolClass = schoolClass;
		initDays(year);
	}


	private void initDays(int year) {
		LocalDate currentDate = LocalDate.of(year, Month.SEPTEMBER, 1);
		LocalDate endDate = currentDate.plusMonths(9);
		while (currentDate.isBefore(endDate)) {
			days.add(new Day(currentDate, this));
			currentDate = currentDate.plusDays(1);
		}
	}
}

