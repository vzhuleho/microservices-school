package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class MarkDTO {

	@ApiModelProperty("A pupil's name")
//	@NotBlank
	private String pupilName;

	@ApiModelProperty("An integer representation of pupil's work")
	@Min(1)
	@Max(10)
	private int value;

	@ApiModelProperty("A comment for the value")
	private String note;

}
