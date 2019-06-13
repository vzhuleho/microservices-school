package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDTO {

	@ApiModelProperty("A name of a pupil which was absent")
	@NotBlank
	private String pupilName;

	@ApiModelProperty("A reason of a pupil's absence")
	private String reason;

}
