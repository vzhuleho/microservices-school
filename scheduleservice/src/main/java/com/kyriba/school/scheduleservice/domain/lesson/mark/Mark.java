package com.kyriba.school.scheduleservice.domain.lesson.mark;

import com.kyriba.school.scheduleservice.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mark  extends BaseEntity {

    private String pupilName;
    private int mark;
}
