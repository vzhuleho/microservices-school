package com.kyriba.school.scheduleservice.domain.lesson.absence;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Absence extends BaseEntity {

    private String pupilName;
}
