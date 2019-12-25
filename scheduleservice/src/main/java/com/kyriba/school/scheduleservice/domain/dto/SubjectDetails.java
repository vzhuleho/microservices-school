package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SubjectDetails {

	private Long id;

	@ApiModelProperty("A subject's name")
	private String name;

}
