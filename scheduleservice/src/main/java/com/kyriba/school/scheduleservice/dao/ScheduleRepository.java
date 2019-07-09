package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    Schedule findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(int year, int grade, String letter);
}
