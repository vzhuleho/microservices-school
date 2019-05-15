package com.kyriba.school.scheduleservice.domain.subject;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subject extends BaseEntity {

	@Column
	private String name;

}
