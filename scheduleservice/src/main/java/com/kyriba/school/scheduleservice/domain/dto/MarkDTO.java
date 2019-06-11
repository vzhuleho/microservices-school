package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel
@Accessors(fluent = true)
public class MarkDTO {

	@ApiModelProperty("A pupil's name")
	@NotBlank
	private String pupilName;

	@ApiModelProperty("An integer representation of pupil's work")
	@Min(1)
	@Max(10)
	private int value;

	@ApiModelProperty("A comment for the value")
	private String note;

}
