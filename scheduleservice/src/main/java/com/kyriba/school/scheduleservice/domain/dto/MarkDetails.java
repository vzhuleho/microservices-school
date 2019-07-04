package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class MarkDetails {

	private Long id;

	@ApiModelProperty("A pupil")
	private PupilDetails pupil;

	@ApiModelProperty("An integer representation of pupil's work")
	private int value;

	@ApiModelProperty("A comment for the value")
	private String note;

	private Long lessonId;

}
