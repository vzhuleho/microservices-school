package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class MarkDTO {

	@ApiModelProperty("A pupil's name")
	@NotBlank
	private String pupilName;

	@ApiModelProperty("A pupil's mark")
	@Min(1)
	@Max(10)
	private int mark;

}
