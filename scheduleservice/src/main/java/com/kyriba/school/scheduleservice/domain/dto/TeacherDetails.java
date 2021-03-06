package com.kyriba.school.scheduleservice.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class TeacherDetails {

	private Long id;

	@ApiModelProperty("A teacher's name")
	@NotBlank
	private String name;

	private Status status;

	@Transient
	private UserInfo userInfo;

	public void setUserInfo(UserInfo userInfo) {
		if (userInfo == null) {
			this.userInfo = null;
			return;
		}
		this.userInfo = userInfo;
		name = userInfo.name;
		status = userInfo.status;
	}

	@Data
	private static class UserInfo {
		private String name;
		private Status status;
	}

	enum Status {
		ACTIVE, INACTIVE
	}
}
