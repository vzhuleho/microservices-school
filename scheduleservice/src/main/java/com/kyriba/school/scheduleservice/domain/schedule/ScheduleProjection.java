package com.kyriba.school.scheduleservice.domain.schedule;

import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "scheduleProjection", types = { Schedule.class })
public interface ScheduleProjection {

    @Value("#{target.id}")
    Long getId();

    int getYear();

    SchoolClassProjection getSchoolClass();
}

