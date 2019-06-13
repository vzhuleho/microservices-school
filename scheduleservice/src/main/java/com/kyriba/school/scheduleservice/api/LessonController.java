package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Api(value = "School Schedules Management System")
@RequestMapping(value = "/api/v1/lessons/{year}/{grade}/{letter}/{date}",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;


    @ApiOperation(value = "List lessons of a day")
    @GetMapping
    public Iterable<LessonDTO> getLessonsByScheduleAndDate(@PathVariable int year,
                                                           @PathVariable int grade,
                                                           @PathVariable String letter,
                                                           @PathVariable String date) {
        return lessonService.getLessons(year, grade, letter, LocalDate.parse(date));
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @GetMapping("/{number}")
    public LessonDTO getLessonByNumber(@PathVariable int year,
                                       @PathVariable int grade,
                                       @PathVariable String letter,
                                       @PathVariable String date,
                                       @PathVariable Integer number) {
        return lessonService.getLesson(year, grade, letter, LocalDate.parse(date), number);
    }

    @ApiOperation(value = "Update a lesson", response = LessonDTO.class)
    @PutMapping("/{number}")
    public LessonDTO updateLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return lessonService.update(lessonDTO);
    }


    @ApiOperation(value = "Retrieve all absences for given lesson", response = AbsenceDTO.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{number}/absences")
    public Iterable<AbsenceDTO> getAbsencesByLesson(@PathVariable int year,
                                                @PathVariable int grade,
                                                @PathVariable String letter,
                                                @PathVariable String date,
                                                @PathVariable Integer number) {
        return lessonService.getAbsencesByLesson(year, grade, letter, LocalDate.parse(date), number);
    }

    @ApiOperation(value = "Add information about pupil's absence to a lesson")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{number}/absences")
    public long addAbsenceToLesson(@PathVariable int year,
                                   @PathVariable int grade,
                                   @PathVariable String letter,
                                   @PathVariable String date,
                                   @PathVariable Integer number,
                                   @RequestBody AbsenceDTO absence) {
        return lessonService.addAbsenceToLesson(year, grade, letter, LocalDate.parse(date), number, absence);
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{number}/absences/{absenceId}")
    public void deleteAbsenceFromLessonById(@PathVariable int year,
                                  @PathVariable int grade,
                                  @PathVariable String letter,
                                  @PathVariable String date,
                                  @PathVariable Integer number,
                                  @PathVariable Long absenceId) {
        lessonService.removeAbsenceFromLesson(year, grade, letter, LocalDate.parse(date), number, absenceId);
    }


    @ApiOperation(value = "Retrieve all marks for given lesson", response = MarkDTO.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{number}/marks")
    public List<MarkDTO> getMarksByLesson(@PathVariable int year,
                                          @PathVariable int grade,
                                          @PathVariable String letter,
                                          @PathVariable String date,
                                          @PathVariable Integer number) {
        return lessonService.getMarksByLesson(year, grade, letter, LocalDate.parse(date), number);
    }

    @ApiOperation(value = "Add information about pupil's mark to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{number}/marks")
    public long addMarkToLesson(@PathVariable int year,
                                @PathVariable int grade,
                                @PathVariable String letter,
                                @PathVariable String date,
                                @PathVariable Integer number,
                                @RequestBody MarkDTO mark) {
        return lessonService.addMarkToLesson(year, grade, letter, LocalDate.parse(date), number, mark);
    }


    @ApiOperation(value = "Delete information about the pupil's mark from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{number}/marks/{markId}")
    public void deleteMarkFromLessonById(@PathVariable int year,
                                         @PathVariable int grade,
                                         @PathVariable String letter,
                                         @PathVariable String date,
                                         @PathVariable Integer number,
                                         @PathVariable Long markId) {
        lessonService.removeMarkFromLesson(year, grade, letter, LocalDate.parse(date), number, markId);
    }
}
