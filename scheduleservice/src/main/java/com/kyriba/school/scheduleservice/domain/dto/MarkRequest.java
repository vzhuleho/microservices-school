package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class MarkRequest {

	private Long id;

	@ApiModelProperty("A pupil's id")
	@NotNull
	private Long pupilId;

	@ApiModelProperty("A pupil's mark")
	@Min(1)
	@Max(10)
	private int value;

	@ApiModelProperty("Any comment")
	private String note;

	@ApiModelProperty("A lesson's id")
	@NotNull
	private Long lessonId;

}
