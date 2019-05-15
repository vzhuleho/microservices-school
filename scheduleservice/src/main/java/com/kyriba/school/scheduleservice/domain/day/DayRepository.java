package com.kyriba.school.scheduleservice.domain.day;

import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

@RepositoryRestResource(exported = false)
public interface DayRepository extends PagingAndSortingRepository<Day, Long> {

    Page<Day> findBySchedule(Schedule schedule, Pageable pageable);

    Day findByScheduleAndDate(Schedule schedule, LocalDate date);

//    Day findByScheduleAndDay(Schedule schedule, DayName day);
}
