package com.kyriba.school.scheduleservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class BaseEntity implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column
	private Long entityId;

	@Override
	@JsonIgnore
	public Long getId() {
		return entityId;
	}
}
