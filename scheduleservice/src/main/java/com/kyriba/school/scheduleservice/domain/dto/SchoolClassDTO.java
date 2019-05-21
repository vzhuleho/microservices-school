package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SchoolClassDTO {

	private Long id;

	@ApiModelProperty("A school class' grade (e.g. 1, 2, 3 etc.)")
	@Min(1)
	@Max(11)
	private int grade;

	@ApiModelProperty("A school class' letter (e.g. A, B, C etc.)")
	@Pattern(regexp = "[A-Za-zА-Яа-я]")
	private String letter;

	@ApiModelProperty("A year of the school class foundation")
	private int foundationYear;

}
