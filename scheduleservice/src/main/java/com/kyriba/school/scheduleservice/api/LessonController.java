package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;


@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules")
@RestController
public class LessonController {

    private final ModelMapper mapper;

    @Autowired
    public LessonController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @ApiOperation(value = "List lessons of a day")
    @GetMapping(value = "/{id}/days/{date}/lessons", produces = "application/hal+json")
    public Iterable<LessonDTO> getLessonsByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setIndex(1);
        lessonDTO.setDate(LocalDate.parse(date));
        return Collections.singletonList(lessonDTO);
    }

    @ApiOperation(value = "Get lesson of a day by its number")
    @GetMapping(value = "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public LessonDTO getLessonByNumber(@PathVariable Long id, @PathVariable String date, @PathVariable Integer number) {
        LessonDTO lesson = new LessonDTO();
        lesson.setIndex(number);
        lesson.setDate(LocalDate.parse(date));
        lesson.setSchoolClass(new SchoolClassDTO(1L, 1, "A", 2019));
        return lesson;
    }

    @ApiOperation(value = "Update a lesson", response = LessonDTO.class)
    @PutMapping(value = "/{id}/days/{date}/lessons/{number}", produces = "application/hal+json")
    public LessonDTO updateLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return lessonDTO;
    }


    @ApiOperation(value = "Add information about pupil's absence to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{id}/days/{date}/lessons/{lessonNumber}/absences", produces = "application/hal+json")
    public AbsenceDTO addAbsenceToLesson(@Valid @RequestBody AbsenceDTO absence) {
        return absence;
    }


    @ApiOperation(value = "Delete information about the pupil's absence from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{scheduleId}/days/{date}/lessons/{lessonNumber}/absences/{absenceId}", produces = "application/hal+json")
    public void deleteAbsenceById(@PathVariable Long scheduleId, @PathVariable String date, @PathVariable Integer lessonNumber, @PathVariable Long absenceId) {
    }


    @ApiOperation(value = "Add information about pupil's mark to a lesson", response = Lesson.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{id}/days/{date}/lessons/{lessonNumber}/marks", produces = "application/hal+json")
    public MarkDTO addAbsenceToLesson(@Valid @RequestBody MarkDTO mark) {
        return mark;
    }


    @ApiOperation(value = "Delete information about the pupil's mark from the lesson")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{scheduleId}/days/{date}/lessons/{lessonNumber}/marks/{markId}", produces = "application/hal+json")
    public void deleteMarkById(@PathVariable Long scheduleId, @PathVariable String date, @PathVariable Integer lessonNumber, @PathVariable Long markId) {
    }
}
