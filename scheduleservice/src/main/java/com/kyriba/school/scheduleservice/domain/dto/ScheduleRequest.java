package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

	private Long id;

	@ApiModelProperty("A year for which this schedule was created")
	@Min(1)
	private int year;

	@ApiModelProperty("A school class ID for which this schedule was created")
	@NotNull
	private Long schoolClassId;

}
