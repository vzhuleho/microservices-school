package com.kyriba.school.scheduleservice.domain.lesson;


import com.kyriba.school.scheduleservice.domain.day.Day;
import com.kyriba.school.scheduleservice.domain.day.DayService;
import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;

@Api(value="School Schedules Management System")
@RestController // only needed for Swagger
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
    @GetMapping(value = SCHEDULES + "/{id}/days/{date}/lessons", produces = "application/hal+json")
    public Iterable<Lesson> getLessonsByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {

        Schedule schedule = scheduleService.findById(id);
        Day day = dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
        return day.getLessons();
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @ResponseBody
    @GetMapping(value = SCHEDULES + "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public Lesson getLessonByNumber(@PathVariable Long id, @PathVariable String date, @PathVariable Integer number) {
        Schedule schedule = scheduleService.findById(id);
        Day day = dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
        return day.getLessons().get(number - 1);
    }

    @ApiOperation(value = "Update a lesson", response = Lesson.class)
    @ResponseBody
    @PatchMapping(value = SCHEDULES  + "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public Lesson updateLesson(@RequestBody Lesson lesson) {
        return lessonService.save(lesson);
    }
}
