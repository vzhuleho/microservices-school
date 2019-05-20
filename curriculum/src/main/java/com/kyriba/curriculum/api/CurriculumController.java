/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;
import com.kyriba.curriculum.service.CurriculumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/api/v1/curricula")
@RequiredArgsConstructor
@Validated
@Api
public class CurriculumController
{
  private final CurriculumService curriculumService;


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all curricula", notes = "Retrieving the collection of curricula", response = BriefCurriculum.class,
      responseContainer = "List")
  public List<BriefCurriculum> getAllCurricula()
  {
    return curriculumService.getAllCurricula();
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get curriculum by id", notes = "Retrieving existing curriculum by its identifier", response = Curriculum.class)
  public Curriculum getCurriculumById(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long id)
  {
    return curriculumService.findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Find existing curriculum by grade", notes = "Retrieving existing curriculum by grade", response = Curriculum.class)
  public Curriculum searchByParameters(@ApiParam("Grade") @GradeConstraint @RequestParam("grade") int grade)
  {
    return curriculumService.findCurriculumByGrade(grade)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED));
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create curriculum", notes = "Create new curriculum", response = BriefCurriculum.class)
  public BriefCurriculum createCurriculum(
      @ApiParam("New curriculum") @Valid @RequestBody CurriculumToCreate curriculumToCreate)
  {
    return curriculumService.createCurriculum(curriculumToCreate);
  }


  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Delete curriculum", notes = "Delete existing curriculum", response = BriefCurriculum.class)
  public BriefCurriculum removeCurriculum(
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
  public Course addCourse(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long curriculumId,
                          @ApiParam("New course") @Valid @RequestBody CourseToAdd course)
  {
    return curriculumService.addCourse(curriculumId, course);
  }


  @DeleteMapping(
      value = "/{curriculumId}/courses/{courseId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ApiOperation(value = "Delete course", notes = "Delete existing course", response = Course.class)
  public Course removeCourse(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId)
  {
    return curriculumService.removeCourse(curriculumId, courseId);
  }


  @PatchMapping(
      value = "/{curriculumId}/courses/{courseId}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ApiOperation(value = "Update lesson count for course", notes = "Update lesson count for existing course", response = Course.class)
  public Course updateLessonCount(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId,
      @ApiParam("Updated lesson count") @Positive @RequestBody int lessonCount)
  {
    return curriculumService.updateLessonCount(curriculumId, courseId, lessonCount);
  }
}
