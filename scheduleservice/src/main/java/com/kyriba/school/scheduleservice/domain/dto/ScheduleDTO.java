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
public class ScheduleDTO {

	private Long id;

	@ApiModelProperty("A year for which this schedule was created")
	private int year;

	@ApiModelProperty("A school class for which this schedule was created")
	@NotNull
	private SchoolClassDTO schoolClass;

}
