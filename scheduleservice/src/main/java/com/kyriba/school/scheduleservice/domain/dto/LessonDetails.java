package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class LessonDetails {

	private Long id;

	@ApiModelProperty("Lesson's date")
	@NotNull
	private LocalDate date;

	@ApiModelProperty("Lesson's number - defines the order of the lessons of a day")
	@NotNull
	@Min(1)
	private int index;

	@ApiModelProperty("A subject of the lesson")
	@NotNull
	private SubjectDetails subject;

	@ApiModelProperty("A teacher who conducts the lesson")
	@NotNull
	private TeacherDetails teacher;

	@ApiModelProperty("A school class which attends the lesson")
	@NotNull
	private SchoolClassDetails schoolClass;

	@ApiModelProperty("A custom note about the lesson")
	private String note;

	@ApiModelProperty("A list of absent pupil")
	@NotNull
	private List<AbsenceDetails> absences = new ArrayList<>();

	@ApiModelProperty("A list of marks which pupils got during the lesson")
	@NotNull
	private List<MarkDetails> marks = new ArrayList<>();

}
