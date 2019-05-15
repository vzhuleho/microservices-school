package com.kyriba.school.scheduleservice.domain.schedule;

import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(excerptProjection = ScheduleProjection.class)
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    @RestResource(exported = false)
    Schedule findByYearAndSchoolClass(int year, SchoolClass schoolClass);

}
