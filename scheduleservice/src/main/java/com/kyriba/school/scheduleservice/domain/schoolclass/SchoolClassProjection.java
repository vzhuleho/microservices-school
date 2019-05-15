package com.kyriba.school.scheduleservice.domain.schoolclass;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "schoolClassProjection", types = { SchoolClass.class })
public interface SchoolClassProjection {

    int getGrade();

    String getLetter();

}
