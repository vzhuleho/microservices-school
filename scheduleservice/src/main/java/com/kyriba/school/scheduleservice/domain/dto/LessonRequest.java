package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ApiModel
public class LessonRequest {

	private Long id;

	@ApiModelProperty("Lesson's date")
	@NotNull
	private LocalDate date;

	@ApiModelProperty("Lesson's number - defines the order of the lessons of a day")
	@NotNull
	@Min(1)
	private int index;

	@ApiModelProperty("A subject ID of the lesson")
	@NotNull
	private long subjectId;

	@ApiModelProperty("An ID of the teacher who conducts the lesson")
	@NotNull
	private long teacherId;

	@ApiModelProperty("An ID of the school class which attends the lesson")
	@NotNull
	private long schoolClassId;

	@ApiModelProperty("A custom note about the lesson")
	private String note;
}
