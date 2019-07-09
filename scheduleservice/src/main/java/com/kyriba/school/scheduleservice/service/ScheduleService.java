package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleDetails;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleRequest;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ModelMapper mapper;

    public Page<ScheduleDetails> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(schedule -> mapper.map(schedule, ScheduleDetails.class));
    }

    public ScheduleDetails findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(schedule -> mapper.map(schedule, ScheduleDetails.class))
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ScheduleDetails find(int year, int grade, String letter) {
        return Optional.ofNullable(scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter))
            .map(schedule -> mapper.map(schedule, ScheduleDetails.class))
            .orElseThrow(() -> new ScheduleNotFoundException(grade, letter, year));
    }

    @Transactional
    public ScheduleDetails create(ScheduleRequest scheduleToCreate) {
        SchoolClass schoolClass = schoolClassRepository.findById(scheduleToCreate.getSchoolClassId())
            .orElseThrow(() -> new SchoolClassNotFoundException(scheduleToCreate.getSchoolClassId()));
        Schedule schedule = scheduleRepository.save(new Schedule(scheduleToCreate.getYear(), schoolClass));
        return mapper.map(schedule, ScheduleDetails.class);
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
