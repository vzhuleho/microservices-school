package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.DayRepository;
import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class DayService {

    private final DayRepository dayRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper mapper;

    public DayDTO find(int year, int grade, String letter, LocalDate date) {
        long scheduleId = getScheduleId(year, grade, letter);
        Day day = dayRepository.findByScheduleIdAndDate(scheduleId, date);
        return mapper.map(day, DayDTO.class);
    }

    public Page<DayDTO> findAll(int year, int grade, String letter, Pageable pageable) {
        long scheduleId = getScheduleId(year, grade, letter);
        Page<Day> days = dayRepository.findByScheduleId(scheduleId, pageable);
        return days.map(day -> mapper.map(day, DayDTO.class));
    }

    private long getScheduleId(int year, int grade, String letter) {
        Schedule schedule = scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter);
        return schedule.getId();
    }
}
