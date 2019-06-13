package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class AbsenceDTO {

	@ApiModelProperty("A name of a pupil which was absent")
//	@NotBlank
	private String pupilName;

	@ApiModelProperty("A reason of a pupil's absence")
	private String reason;

}
