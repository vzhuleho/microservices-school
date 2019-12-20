package com.kyriba.school.scheduleservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class  Teacher {

	@Id
	@Column
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Status status;

	public enum Status {
		ACTIVE, INACTIVE
	}
}
