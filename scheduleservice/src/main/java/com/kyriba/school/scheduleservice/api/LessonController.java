package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.*;
import com.kyriba.school.scheduleservice.service.DayService;
import com.kyriba.school.scheduleservice.service.LessonService;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import com.kyriba.school.scheduleservice.service.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Api(value = "School Schedules Management System")
@RequestMapping(value = "/api/v1/schedules/{year}/{grade}/{letter}/days/{date}/lessons",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final DayService dayService;
    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final ModelMapper mapper;

    @ApiOperation(value = "List lessons of a day")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Iterable<LessonDTO> getLessonsByScheduleAndDate(@PathVariable int year,
                                                           @PathVariable int grade,
                                                           @PathVariable String letter,
                                                           @PathVariable String date) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        return mapper.map(day, DayDTO.class).getLessons();
    }

    @ApiOperation(value = "Get lesson of a day by its number", response = LessonDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{number}")
    public LessonDTO getLessonByNumber(@PathVariable int year,
                                       @PathVariable int grade,
                                       @PathVariable String letter,
                                       @PathVariable String date,
                                       @PathVariable Integer number) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        Lesson lesson = day.getLessons().get(number - 1);
        return mapper.map(lesson, LessonDTO.class);
    }

    @ApiOperation(value = "Update a lesson", response = LessonDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{number}")
    public LessonDTO updateLesson(@PathVariable int year,
                                  @PathVariable int grade,
                                  @PathVariable String letter,
                                  @PathVariable String date,
                                  @PathVariable Integer number,
                                  @Valid @RequestBody LessonDTO lessonDTO) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        Lesson lesson = day.getLessons().get(number - 1);
        lesson.setNote(lessonDTO.getNote());
        return mapper.map(lessonService.save(lesson), LessonDTO.class);
    }


    @ApiOperation(value = "Add information about pupil's absence to a lesson", response = AbsenceDTO.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{number}/absences")
    public long addAbsenceToLesson(@Valid @RequestBody AbsenceDTO absence) {
//        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
//        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
//        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
//        Lesson lesson = day.getLessons().get(number - 1);
//        lesson.getAbsences().add(mapper.map(absence, Absence.class));
//        lessonService.save(lesson);
        return 1;
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{number}/absences/{absenceId}")
    public void deleteAbsenceById(@PathVariable Integer year,
                                  @PathVariable Integer grade,
                                  @PathVariable String letter,
                                  @PathVariable String date,
                                  @PathVariable Integer number,
                                  @PathVariable Long absenceId) {
    }


    @ApiOperation(value = "Add information about pupil's value to a lesson", response = MarkDTO.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{number}/marks")
    public MarkDTO addMarkToLesson(@PathVariable int number,
                                   @Valid @RequestBody MarkDTO mark) {
//        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
//        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
//        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
//        Lesson lesson = day.getLessons().get(number - 1);
//        lesson.getMarks().add(mapper.map(value, Mark.class));
//        lessonService.save(lesson);
        return mark;
    }


    @ApiOperation(value = "Retrieve all marks for given lesson", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{number}/marks")
    public List<MarkDTO> getMarksByLesson(@PathVariable int year,
                                         @PathVariable int grade,
                                         @PathVariable String letter,
                                         @PathVariable String date,
                                         @PathVariable int number) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        Lesson lesson = day.getLessons().get(number - 1);
        return lesson.getMarks().stream().map(mark -> mapper.map(mark, MarkDTO.class)).collect(Collectors.toList());
    }


    @ApiOperation(value = "Delete information about the pupil's value from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{number}/marks/{markId}")
    public void deleteMarkById(@PathVariable int year,
                               @PathVariable int grade,
                               @PathVariable String letter,
                               @PathVariable String date,
                               @PathVariable Integer number,
                               @PathVariable Long markId) {
    }
}
