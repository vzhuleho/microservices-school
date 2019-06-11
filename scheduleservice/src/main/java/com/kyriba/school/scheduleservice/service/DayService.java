package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.DayRepository;
import com.kyriba.school.scheduleservice.domain.entity.Day;
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

    public Page<Day> findByScheduleId(long scheduleId, Pageable pageable) {
        return dayRepository.findByScheduleId(scheduleId, pageable);
    }

    public Day findByScheduleIdAndDate(long scheduleId, LocalDate date) {
        return dayRepository.findByScheduleIdAndDate(scheduleId, date);
    }

}
