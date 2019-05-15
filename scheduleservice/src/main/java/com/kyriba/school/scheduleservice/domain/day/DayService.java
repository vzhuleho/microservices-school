package com.kyriba.school.scheduleservice.domain.day;

import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DayService {

    private final DayRepository dayRepository;

    @Autowired
    private DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public Page<Day> findBySchedule(Schedule schedule, Pageable pageable) {
        return dayRepository.findBySchedule(schedule, pageable);
    }

    public Day findByScheduleAndDate(Schedule schedule, LocalDate date) {
        return dayRepository.findByScheduleAndDate(schedule, date);
    }
}
