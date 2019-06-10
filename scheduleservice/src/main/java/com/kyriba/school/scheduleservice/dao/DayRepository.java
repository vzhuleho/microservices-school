package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

@RepositoryRestResource(exported = false)
public interface DayRepository extends PagingAndSortingRepository<Day, Long> {

    Page<Day> findByScheduleId(long scheduleId, Pageable pageable);

    Day findByScheduleIdAndDate(long scheduleId, LocalDate date);

}
