package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDetails;
import com.kyriba.school.scheduleservice.domain.dto.LessonDetails;
import com.kyriba.school.scheduleservice.domain.dto.LessonRequest;
import com.kyriba.school.scheduleservice.domain.dto.MarkDetails;
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
@RequestMapping(value = "/api/v1/lessons",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;


    @ApiOperation(value = "List lessons of a day for a particular schedule (by its year, grade and letter)")
    @GetMapping(value = "/{year}/{grade}/{letter}/{date}", produces = "application/hal+json")
    @ResponseBody
    public Iterable<LessonDetails> getLessonsByScheduleAndDate(@PathVariable int year,
                                                               @PathVariable int grade,
                                                               @PathVariable String letter,
                                                               @PathVariable String date) {
        return lessonService.getLessons(year, grade, letter, LocalDate.parse(date));
    }

   @ApiOperation(value = "Update a lesson", response = LessonRequest.class)
    @PutMapping("/{id}")
    public LessonDetails updateLesson(@Valid @RequestBody LessonRequest lessonRequest) {
        return lessonService.update(lessonRequest);
    }

    @ApiOperation(value = "Retrieve all absences for given lesson", response = AbsenceDetails.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/absences")
    public Iterable<AbsenceDetails> getAbsencesByLesson(@PathVariable long id) {
        return lessonService.getAbsencesByLesson(id);
    }

    @ApiOperation(value = "Retrieve all marks for given lesson", response = MarkDetails.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/marks")
    public List<MarkDetails> getMarksByLesson(@PathVariable long id) {
        return lessonService.getMarksByLesson(id);
    }

}
