package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.DayRepository;
import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final ModelMapper mapper;

    @Transactional
    public LessonDTO update(LessonDTO lessonDTO) {
        SchoolClassDTO schoolClass = lessonDTO.getSchoolClass();
        Day day  = getDay(lessonDTO.getDate().getYear(), schoolClass.getGrade(), schoolClass.getLetter(), lessonDTO.getDate());
        Lesson lesson = getLessonByNumber(day, lessonDTO.getIndex());
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

    public LessonDTO getLesson(int year, int grade, String letter, LocalDate date, Integer number) {
        Day day = getDay(year, grade, letter, date);
        Lesson lesson = getLessonByNumber(day, number);
        return mapper.map(lesson, LessonDTO.class);
    }

    private Day getDay(int year, int grade, String letter, LocalDate date) {
        Schedule schedule = scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter);
        return dayRepository.findByScheduleIdAndDate(schedule.getId(), date);
    }

    private Lesson getLessonByNumber(Day day, int number) {
        return day.getLessons().get(number - 1);
    }
}
