package com.kyriba.school.scheduleservice.domain.teacher;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends BaseEntity {

	@Column(nullable = false)
	private String name;
}
