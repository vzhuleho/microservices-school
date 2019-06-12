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

    public ScheduleDTO findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(schedule -> mapper.map(schedule, ScheduleDTO.class))
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ScheduleDTO findOrCreate(ScheduleDTO scheduleDTO) {
        return findOrCreate(scheduleDTO.getYear(), scheduleDTO.getSchoolClass());
    }

    @Transactional
    public ScheduleDTO findOrCreate(int year, int grade, String letter) {
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO()
            .setGrade(grade)
            .setLetter(letter)
            .setFoundationYear(SchoolClass.currentToFoundationYear(year, grade));
        return findOrCreate(year, schoolClassDTO);
    }

    public Page<ScheduleDTO> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(schedule -> mapper.map(schedule, ScheduleDTO.class));
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO findOrCreate(int year, SchoolClassDTO schoolClass) {
        return find(year, schoolClass).orElseGet(() -> create(year, schoolClass));
    }

    private ScheduleDTO create(int year, SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = getSchoolClassFromRepository(schoolClassDTO);
        Schedule schedule = scheduleRepository.save(new Schedule(year, schoolClass));
        return mapper.map(schedule, ScheduleDTO.class);
    }

    private Optional<ScheduleDTO> find(int year, SchoolClassDTO schoolClassDTO) {
        return Optional.ofNullable(scheduleRepository.findByYearAndSchoolClass(year, getSchoolClassFromRepository(schoolClassDTO)))
            .map(schedule -> mapper.map(schedule, ScheduleDTO.class));
    }

    private SchoolClass getSchoolClassFromRepository(SchoolClassDTO schoolClassDTO) {
        int grade = schoolClassDTO.getGrade();
        String letter = schoolClassDTO.getLetter();
        int foundationYear = schoolClassDTO.getFoundationYear();
        return Optional.ofNullable(schoolClassRepository.findByGradeAndLetterIgnoreCaseAndFoundationYear(grade, letter, foundationYear))
            .orElseThrow(() -> new SchoolClassNotFoundException(grade, letter, foundationYear));
    }
}
