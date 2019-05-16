package com.kyriba.school.scheduleservice.domain.lesson;


import com.kyriba.school.scheduleservice.domain.day.Day;
import com.kyriba.school.scheduleservice.domain.day.DayService;
import com.kyriba.school.scheduleservice.domain.lesson.absence.Absence;
import com.kyriba.school.scheduleservice.domain.lesson.mark.Mark;
import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;

@Api(value="School Schedules Management System")
@RequestMapping(SCHEDULES)
@RepositoryRestController
public class LessonController {

    private final ScheduleService scheduleService;
    private final DayService dayService;
    private final LessonService lessonService;

    @Autowired
    public LessonController(ScheduleService scheduleService,
                            DayService dayService,
                            LessonService lessonService) {
        this.scheduleService = scheduleService;
        this.dayService = dayService;
        this.lessonService = lessonService;
    }

    @ApiOperation(value = "List lessons of a day")
    @ResponseBody
    @GetMapping(value = "/{id}/days/{date}/lessons", produces = "application/hal+json")
    public Iterable<Lesson> getLessonsByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {

        Schedule schedule = scheduleService.findById(id);
        Day day = dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
        return day.getLessons();
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @ResponseBody
    @GetMapping(value = "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public Lesson getLessonByNumber(@PathVariable Long id, @PathVariable String date, @PathVariable Integer number) {
        Schedule schedule = scheduleService.findById(id);
        Day day = dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
        return day.getLessons().get(number - 1);
    }

    @ApiOperation(value = "Update a lesson", response = Lesson.class)
    @ResponseBody
    @PatchMapping(value = "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public Lesson updateLesson(@RequestBody Lesson lesson) {
        return lessonService.save(lesson);
    }


    @ApiOperation(value = "Add information about pupil's absence to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(value = "/{id}/days/{date}/lessons/{lessonNumber}/absences", produces = "application/hal+json")
    public Absence addAbsenceToLesson(@RequestBody Absence absence) {
        return absence;
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{scheduleId}/days/{date}/lessons/{lessonNumber}/absences/{absenceId}", produces = "application/hal+json")
    public void deleteAbsenceById(@PathVariable Long scheduleId, @PathVariable String date, @PathVariable Integer lessonNumber, @PathVariable Long absenceId) {
        return;
    }


    @ApiOperation(value = "Add information about pupil's mark to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(value = "/{id}/days/{date}/lessons/{lessonNumber}/marks", produces = "application/hal+json")
    public Mark addAbsenceToLesson(@RequestBody Mark mark) {
        return mark;
    }


    @ApiOperation(value = "Delete information about the pupil's mark from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{scheduleId}/days/{date}/lessons/{lessonNumber}/marks/{markId}", produces = "application/hal+json")
    public void deleteMarkById(@PathVariable Long scheduleId, @PathVariable String date, @PathVariable Integer lessonNumber, @PathVariable Long markId) {
    }
}
