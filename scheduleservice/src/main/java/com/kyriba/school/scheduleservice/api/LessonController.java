package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import com.kyriba.school.scheduleservice.service.DayService;
import com.kyriba.school.scheduleservice.service.LessonService;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import com.kyriba.school.scheduleservice.service.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;


@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules/{year}/{grade}/{letter}/days/{date}/lessons")
@RestController
public class LessonController {

    private final LessonService lessonService;
    private final DayService dayService;
    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final ModelMapper mapper;

    @Autowired
    public LessonController(LessonService lessonService, DayService dayService, ScheduleService scheduleService, SchoolClassService schoolClassService, ModelMapper mapper) {
        this.lessonService = lessonService;
        this.dayService = dayService;
        this.scheduleService = scheduleService;
        this.schoolClassService = schoolClassService;
        this.mapper = mapper;
    }

    @ApiOperation(value = "List lessons of a day")
    @GetMapping(produces = "application/hal+json")
    public Iterable<LessonDTO> getLessonsByScheduleAndDate(@PathVariable int year,
                                                           @PathVariable int grade,
                                                           @PathVariable String letter,
                                                           @PathVariable String date) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        return mapper.map(day, DayDTO.class).getLessons();
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @GetMapping(value = "/{number}", produces = "application/hal+json")
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
    @PutMapping(value = "/{number}", produces = "application/hal+json")
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


    @ApiOperation(value = "Add information about pupil's absence to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{number}/absences", produces = "application/hal+json")
    public long addAbsenceToLesson(@Valid @RequestBody AbsenceDTO absence) {
        return 1;
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{number}/absences/{absenceId}", produces = "application/hal+json")
    public void deleteAbsenceById(@PathVariable int year,
                                  @PathVariable int grade,
                                  @PathVariable String letter,
                                  @PathVariable String date,
                                  @PathVariable Integer number,
                                  @PathVariable Long absenceId) {
    }


    @ApiOperation(value = "Add information about pupil's mark to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{number}/marks", produces = "application/hal+json")
    public long addMarkToLesson(@Valid @RequestBody MarkDTO mark) {
        return 1;
    }


    @ApiOperation(value = "Delete information about the pupil's mark from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{number}/marks/{markId}", produces = "application/hal+json")
    public void deleteMarkById(@PathVariable int year,
                               @PathVariable int grade,
                               @PathVariable String letter,
                               @PathVariable String date,
                               @PathVariable Integer number,
                               @PathVariable Long markId) {
    }
}
