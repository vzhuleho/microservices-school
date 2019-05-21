/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;
import com.kyriba.curriculum.service.CurriculumService;
import com.kyriba.curriculum.service.SubjectService;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/api/v1/curricula")
@RequiredArgsConstructor
@Validated
@Api
class CurriculumController
{
  private final CurriculumService curriculumService;
  private final SubjectService subjectService;


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all curricula", notes = "Retrieving the collection of curricula", response = BriefCurriculum.class,
      responseContainer = "List")
  List<BriefCurriculum> getAllCurricula()
  {
    return curriculumService.getAllCurricula();
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get curriculum by id", notes = "Retrieving existing curriculum by its identifier", response = Curriculum.class)
  Curriculum getCurriculumById(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long id)
  {
    return curriculumService.findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Find existing curriculum by grade", notes = "Retrieving existing curriculum by grade", response = Curriculum.class)
  Curriculum searchByParameters(@ApiParam("Grade") @GradeConstraint @RequestParam("grade") int grade)
  {
    return curriculumService.findCurriculumByGrade(grade)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED));
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create curriculum", notes = "Create new curriculum", response = BriefCurriculum.class)
  BriefCurriculum createCurriculum(
      @ApiParam("New curriculum") @Valid @RequestBody CurriculumToCreate curriculumToCreate)
  {
    return curriculumService.createCurriculum(curriculumToCreate);
  }


  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Delete curriculum", notes = "Delete existing curriculum", response = BriefCurriculum.class)
  BriefCurriculum removeCurriculum(
      @ApiParam("Identifier of curriculum to delete") @PathVariable("id") long curriculumId)
  {
    return curriculumService.removeCurriculum(curriculumId);
  }


  @PostMapping(
      value = "/{id}/courses",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Add new course", notes = "Add new course to existing curriculum", response = Course.class)
  Course addCourse(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long curriculumId,
                          @ApiParam("New course") @Valid @RequestBody CourseToAdd course)
  {
    return curriculumService.addCourse(curriculumId, course);
  }


  @DeleteMapping(
      value = "/{curriculumId}/courses/{courseId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete course", notes = "Delete existing course", response = Course.class)
  void removeCourse(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId)
  {
    curriculumService.removeCourse(curriculumId, courseId);
  }


  @PutMapping(
      value = "/{curriculumId}/courses/{courseId}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ApiOperation(value = "Update lesson count for course", notes = "Update lesson count for existing course", response = Course.class)
  ResponseEntity<Course> updateCourse(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId,
      @ApiParam("Updated course") @Valid @RequestBody CourseToUpdate courseToUpdate)
  {
    Curriculum curriculum = curriculumService.findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    Subject subject = subjectService.getSubjectById(courseToUpdate.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToUpdate.getSubjectId()));

    if (curriculum.getCourses().stream()
        .anyMatch(it -> it.getSubject().equals(subject) && it.getId() != courseId))
      throw new CourseAlreadyExistsException(curriculumId, courseToUpdate.getSubjectId());

    Course updatedCourse = new Course(courseId, subject, courseToUpdate.getLessonCount());
    boolean removed = curriculum.getCourses().removeIf(it -> it.getId() == courseId);
    curriculum.getCourses().add(updatedCourse);
    return new ResponseEntity<>(updatedCourse, removed ? HttpStatus.OK : HttpStatus.CREATED);
  }
}
