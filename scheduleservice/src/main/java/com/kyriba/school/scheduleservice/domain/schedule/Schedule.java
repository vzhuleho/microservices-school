package com.kyriba.school.scheduleservice.domain.schedule;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kyriba.school.scheduleservice.domain.BaseEntity;
import com.kyriba.school.scheduleservice.domain.day.Day;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Schedule extends BaseEntity {

	@Column(nullable = false)
	private int year;

	@OneToOne
	@JoinColumn(nullable = false)
	private SchoolClass schoolClass;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	@JsonBackReference
	private List<Day> days = new ArrayList<>();

	public Schedule(int year, SchoolClass schoolClass) {
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

