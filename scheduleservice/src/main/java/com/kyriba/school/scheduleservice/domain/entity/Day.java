package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Day {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column
	private LocalDate date;

	@ManyToOne
	@JoinColumn
	private Schedule schedule;

	@Getter
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("index")
	private List<Lesson> lessons = new ArrayList<>(8);

	public Day(LocalDate date, Schedule schedule) {
		this.date = date;
		this.schedule = schedule;
		initLessons();
	}


	private void initLessons() {
		for (int i = 1; i < 9; i++) {
			lessons.add(new Lesson(date, i, schedule.getSchoolClass()));
		}
	}


	public void setLesson(Lesson lesson) {
		lessons.set(lesson.getIndex() - 1, lesson);
	}
}

