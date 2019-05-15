package com.kyriba.school.scheduleservice.domain.day;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import com.kyriba.school.scheduleservice.domain.lesson.Lesson;
import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.subject.Subject;
import com.kyriba.school.scheduleservice.domain.teacher.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Day extends BaseEntity {

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

