package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel
public class SchoolClassDetails {

	private Long id;

	@ApiModelProperty("A school class' grade (e.g. 1, 2, 3 etc.)")
	private int grade;

	@ApiModelProperty("A school class' letter (e.g. A, B, C etc.)")
	private String letter;

	@ApiModelProperty("A year of the school class foundation")
	private int year;

	@ApiModelProperty("Pupils list of this class")
	private List<PupilDetails> pupils = new ArrayList<>();

}
