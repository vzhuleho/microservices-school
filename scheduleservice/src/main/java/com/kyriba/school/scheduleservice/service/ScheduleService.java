package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
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

    public Page<ScheduleDTO> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(schedule -> mapper.map(schedule, ScheduleDTO.class));
    }

    public ScheduleDTO findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(schedule -> mapper.map(schedule, ScheduleDTO.class))
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ScheduleDTO find(int year, int grade, String letter) {
        return Optional.ofNullable(scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter))
            .map(schedule -> mapper.map(schedule, ScheduleDTO.class))
            .orElseThrow(() -> new ScheduleNotFoundException(grade, letter, year));
    }

    @Transactional
    public ScheduleDTO create(ScheduleDTO scheduleToCreate) {
        SchoolClass schoolClass = getSchoolClassFromRepository(scheduleToCreate.getSchoolClass());
        Schedule schedule = scheduleRepository.save(new Schedule(scheduleToCreate.getYear(), schoolClass));
        return mapper.map(schedule, ScheduleDTO.class);
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    private SchoolClass getSchoolClassFromRepository(SchoolClassDTO schoolClassDTO) {
        int grade = schoolClassDTO.getGrade();
        String letter = schoolClassDTO.getLetter();
        int foundationYear = schoolClassDTO.getFoundationYear();
        return Optional.ofNullable(schoolClassRepository.findByGradeAndLetterIgnoreCaseAndFoundationYear(grade, letter, foundationYear))
            .orElseThrow(() -> new SchoolClassNotFoundException(grade, letter, foundationYear));
    }
}
