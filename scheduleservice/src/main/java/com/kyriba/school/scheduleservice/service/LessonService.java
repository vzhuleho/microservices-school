package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.*;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
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
    private final MarkRepository markRepository;
    private final AbsenceRepository absenceRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final PupilRepository pupilRepository;
    private final ModelMapper mapper;

    @Transactional
    public LessonDTO update(LessonDTO lessonDTO) {
        SchoolClassDTO schoolClass = lessonDTO.getSchoolClass();
        Day day = getDay(lessonDTO.getDate().getYear(), schoolClass.getGrade(), schoolClass.getLetter(), lessonDTO.getDate());
        Lesson lesson = getLessonByNumber(day, lessonDTO.getIndex());
        lesson.updateFields(lessonDTO);
        Lesson savedLesson = lessonRepository.save(lesson);
        return mapper.map(savedLesson, LessonDTO.class);
    }

    public LessonDTO getLesson(int year, int grade, String letter, LocalDate date, Integer number) {
        return mapper.map(getLessonFromSchedule(year, grade, letter, date, number), LessonDTO.class);
    }

    public Iterable<LessonDTO> getLessons(int year, int grade, String letter, LocalDate date) {
        Day day = getDay(year, grade, letter, date);
        return day.getLessons().stream()
                .map(lesson -> mapper.map(lesson, LessonDTO.class))
                .collect(Collectors.toList());
    }

    private Lesson getLessonFromSchedule(int year, int grade, String letter, LocalDate date, Integer number) {
        Day day = getDay(year, grade, letter, date);
        return getLessonByNumber(day, number);
    }

    private Day getDay(int year, int grade, String letter, LocalDate date) {
        Schedule schedule = scheduleRepository.findByYearAndSchoolClassGradeAndSchoolClassLetterIgnoreCase(year, grade, letter);
        return dayRepository.findByScheduleIdAndDate(schedule.getId(), date);
    }

    private Lesson getLessonByNumber(Day day, int number) {
        return day.getLessons().get(number - 1);
    }

    private List<MarkDTO> getMarksByLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getMarks().stream()
                .map(mark -> new MarkDTO(mark.pupil().name(), mark.value(), mark.note()))
                .collect(Collectors.toList());
    }

    public List<MarkDTO> getMarksByLesson(int year, int grade, String letter, LocalDate date, Integer number) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        return getMarksByLesson(lesson.getId());
    }

    private Long addMarkToLesson(Long lessonId, MarkDTO markDTO) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.getByNameAndSchoolClass(markDTO.pupilName(), lesson.getSchoolClass())
                .orElseThrow(ResourceNotFoundException::new);
        return markRepository.save(new Mark(markDTO.value(), markDTO.note(), pupil, lesson)).id();
    }

    @Transactional
    public Long addMarkToLesson(int year, int grade, String letter, LocalDate date, Integer number, MarkDTO markDTO) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        return addMarkToLesson(lesson.getId(), markDTO);
    }

    private void removeMarkFromLesson(Long lessonId, Long markId) {
        lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new)
                .getMarks().stream()
                .map(Mark::id)
                .filter(markId::equals)
                .findFirst()
                .ifPresent(markRepository::deleteById);
    }

    @Transactional
    public void removeMarkFromLesson(int year, int grade, String letter, LocalDate date, Integer number, Long markId) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        removeMarkFromLesson(lesson.getId(), markId);
    }

    private List<AbsenceDTO> getAbsencesByLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getAbsences().stream()
                .map(absence -> new AbsenceDTO(absence.pupil().name(), absence.reason()))
                .collect(Collectors.toList());
    }

    public List<AbsenceDTO> getAbsencesByLesson(int year, int grade, String letter, LocalDate date, Integer number) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        return getAbsencesByLesson(lesson.getId());
    }

    private Long addAbsenceToLesson(Long lessonId, AbsenceDTO absenceDTO) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.getByNameAndSchoolClass(absenceDTO.pupilName(), lesson.getSchoolClass())
                .orElseThrow(ResourceNotFoundException::new);
        return absenceRepository.save(new Absence(absenceDTO.reason(), pupil, lesson)).id();
    }

    @Transactional
    public Long addAbsenceToLesson(int year, int grade, String letter, LocalDate date, Integer number, AbsenceDTO absenceDTO) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        return addAbsenceToLesson(lesson.getId(), absenceDTO);
    }

    private void removeAbsenceFromLesson(Long lessonId, Long absenceId) {
        lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new)
                .getAbsences().stream()
                .map(Absence::id)
                .filter(absenceId::equals)
                .findFirst()
                .ifPresent(absenceRepository::deleteById);
    }

    @Transactional
    public void removeAbsenceFromLesson(int year, int grade, String letter, LocalDate date, Integer number, Long absenceId) {
        Lesson lesson = getLessonFromSchedule(year, grade, letter, date, number);
        removeAbsenceFromLesson(lesson.getId(), absenceId);
    }
}
