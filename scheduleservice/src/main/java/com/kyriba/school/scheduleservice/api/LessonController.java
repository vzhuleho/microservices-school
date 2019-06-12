package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @ApiOperation(value = "List lessons of a day")
    @GetMapping(produces = "application/hal+json")
    public Iterable<LessonDTO> getLessonsByScheduleAndDate(@PathVariable int year,
                                                           @PathVariable int grade,
                                                           @PathVariable String letter,
                                                           @PathVariable String date) {
        return lessonService.getLessons(year, grade, letter, LocalDate.parse(date));
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @GetMapping(value = "/{number}", produces = "application/hal+json")
    public LessonDTO getLessonByNumber(@PathVariable int year,
                                       @PathVariable int grade,
                                       @PathVariable String letter,
                                       @PathVariable String date,
                                       @PathVariable Integer number) {
        return lessonService.getLesson(year, grade, letter, LocalDate.parse(date), number);
    }

    @ApiOperation(value = "Update a lesson", response = LessonDTO.class)
    @PutMapping(value = "/{number}", produces = "application/hal+json")
    public LessonDTO updateLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return lessonService.update(lessonDTO);
    }

    @ApiOperation(value = "Add information about pupil's absence to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{lessonNumber}/absences", produces = "application/hal+json")
    public long addAbsenceToLesson(@Valid @RequestBody AbsenceDTO absence) {
        return 1;
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{lessonNumber}/absences/{absenceId}", produces = "application/hal+json")
    public void deleteAbsenceById(@PathVariable int year,
                                  @PathVariable int grade,
                                  @PathVariable String letter,
                                  @PathVariable String date,
                                  @PathVariable Integer lessonNumber,
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
