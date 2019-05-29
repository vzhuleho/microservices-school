package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class DayDTO {

	@ApiModelProperty
	@NotNull
	private LocalDate date;

	@ApiModelProperty("A schedule to which this day pertains")
	@NotNull
	private ScheduleDTO schedule;

	@ApiModelProperty("A list of the lessons for this day")
	@NotNull
	private List<LessonDTO> lessons = new ArrayList<>();

}
