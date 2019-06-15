package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.*;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MarkRepository markRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final PupilRepository pupilRepository;
    private final ModelMapper mapper;

    @Transactional
    public LessonDTO update(LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(lessonDTO.getId()).orElseThrow(ResourceNotFoundException::new);
        lesson.updateFields(lessonDTO);
        Lesson savedLesson = lessonRepository.save(lesson);
        return mapper.map(savedLesson, LessonDTO.class);
    }

    public Iterable<LessonDTO> getLessons(int year, int grade, String letter, LocalDate date) {
        Day day = getDay(year, grade, letter, date);
        return day.getLessons().stream()
                .map(lesson -> mapper.map(lesson, LessonDTO.class))
                .collect(Collectors.toList());
    }

    private Day getDay(int year, int grade, String letter, LocalDate date) {
        Schedule schedule = scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter);
        return dayRepository.findByScheduleIdAndDate(schedule.getId(), date);
    }

    public List<MarkDTO> getMarksByLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
            .map(Lesson::getMarks)
            .orElse(Collections.emptyList())
            .stream()
            .map(Mark::output)
            .collect(Collectors.toList());
    }



    @Transactional
    public void removeMarkFromLesson(Long markId) {
        markRepository.deleteById(markId);
    }

    public Iterable<AbsenceDTO> getAbsencesByLesson(long lessonId) {
        return lessonRepository.findById(lessonId)
            .map(Lesson::getAbsences)
            .stream()
            .map(absence -> mapper.map(absence, AbsenceDTO.class))
            .collect(Collectors.toList());
    }
}
