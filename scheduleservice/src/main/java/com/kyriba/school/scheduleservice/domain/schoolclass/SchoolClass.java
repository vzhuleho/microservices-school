package com.kyriba.school.scheduleservice.domain.schoolclass;

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
public class SchoolClass extends BaseEntity {

	@Column(nullable = false)
	private int grade;

	@Column(nullable = false)
	private String letter;

	@Column(nullable = false)
	private int foundationYear;
}
