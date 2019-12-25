package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceRequest {

	private Long id;

	@ApiModelProperty("A pupil's id")
	@NotNull
	private Long pupilId;

	@ApiModelProperty("A lesson's id")
	@NotNull
	private Long lessonId;

	@ApiModelProperty("A reason of a pupil's absence")
	private String reason;
}
