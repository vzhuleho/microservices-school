package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.DayRepository;
import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.dataprovider.CurriculumDataProvider;
import com.kyriba.school.scheduleservice.dataprovider.UserDataProvider;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceDetails;
import com.kyriba.school.scheduleservice.domain.dto.LessonDetails;
import com.kyriba.school.scheduleservice.domain.dto.LessonRequest;
import com.kyriba.school.scheduleservice.domain.dto.MarkDetails;
import com.kyriba.school.scheduleservice.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final ModelMapper mapper;
    private final UserDataProvider userDataProvider;
    private final CurriculumDataProvider curriculumDataProvider;

    @Transactional
    public LessonDetails update(LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonRequest.getId()).orElseThrow(ResourceNotFoundException::new);
        Subject subject = mapper.map(curriculumDataProvider.getSubject(lessonRequest.getSubjectId()), Subject.class);
        Teacher teacher = mapper.map(userDataProvider.getTeacher(lessonRequest.getTeacherId()), Teacher.class);
        lesson.setNote(lessonRequest.getNote()).setTeacher(teacher).setSubject(subject);
        Lesson savedLesson = lessonRepository.save(lesson);
        return mapper.map(savedLesson, LessonDetails.class);
    }

    public Iterable<LessonDetails> getLessons(int year, int grade, String letter, LocalDate date) {
        Day day = getDay(year, grade, letter, date);
        return day.getLessons().stream()
                .map(lesson -> mapper.map(lesson, LessonDetails.class))
                .collect(Collectors.toList());
    }

    private Day getDay(int year, int grade, String letter, LocalDate date) {
        Schedule schedule = scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter);
        return dayRepository.findByScheduleIdAndDate(schedule.getId(), date);
    }

    public List<MarkDetails> getMarksByLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
            .map(Lesson::getMarks)
            .stream()
            .map(mark -> mapper.map(mark, MarkDetails.class))
            .collect(Collectors.toList());
    }

    public Iterable<AbsenceDetails> getAbsencesByLesson(long lessonId) {
        return lessonRepository.findById(lessonId)
            .map(Lesson::getAbsences)
            .stream()
            .map(absence -> mapper.map(absence, AbsenceDetails.class))
            .collect(Collectors.toList());
    }
}
