package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(ResourceNotFoundException::new);
    }

    public Schedule findOrCreate(int year, SchoolClass schoolClass) {
        return find(year, schoolClass).orElseGet(() -> create(year, schoolClass));
    }

    public Page<Schedule> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    private Schedule create(int year, SchoolClass schoolClass) {
        return scheduleRepository.save(new Schedule(year, schoolClass));
    }

    private Optional<Schedule> find(int year, SchoolClass schoolClass) {
        return Optional.ofNullable(scheduleRepository.findByYearAndSchoolClass(year, schoolClass));
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

}
