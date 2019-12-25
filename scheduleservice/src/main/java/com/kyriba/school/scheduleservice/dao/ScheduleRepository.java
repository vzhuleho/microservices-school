package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    Schedule findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(int year, int grade, String letter);
}
