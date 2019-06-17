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
@RequestMapping(value = "/api/v1/lessons",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;


    @ApiOperation(value = "List lessons of a day for a particular schedule (by its year, grade and letter)")
    @GetMapping(value = "/{year}/{grade}/{letter}/{date}", produces = "application/hal+json")
    public Iterable<LessonDTO> getLessonsByScheduleAndDate(@PathVariable int year,
                                                           @PathVariable int grade,
                                                           @PathVariable String letter,
                                                           @PathVariable String date) {
        return lessonService.getLessons(year, grade, letter, LocalDate.parse(date));
    }

   @ApiOperation(value = "Update a lesson", response = LessonDTO.class)
    @PutMapping("/{id}")
    public LessonDTO updateLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return lessonService.update(lessonDTO);
    }

    @ApiOperation(value = "Retrieve all absences for given lesson", response = AbsenceDTO.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/absences")
    public Iterable<AbsenceDTO> getAbsencesByLesson(@PathVariable long id) {
        return lessonService.getAbsencesByLesson(id);
    }

    @ApiOperation(value = "Retrieve all marks for given lesson", response = MarkDTO.class, responseContainer = "List")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/marks")
    public List<MarkDTO> getMarksByLesson(@PathVariable long id) {
        return lessonService.getMarksByLesson(id);
    }

}
