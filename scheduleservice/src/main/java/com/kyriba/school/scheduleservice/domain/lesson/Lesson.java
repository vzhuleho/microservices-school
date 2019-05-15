package com.kyriba.school.scheduleservice.domain.lesson;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.subject.Subject;
import com.kyriba.school.scheduleservice.domain.teacher.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Lesson extends BaseEntity {


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

	public Lesson(LocalDate date, int index, SchoolClass schoolClass) {
		this.date = date;
		this.index = index;
		this.schoolClass = schoolClass;
	}
}
