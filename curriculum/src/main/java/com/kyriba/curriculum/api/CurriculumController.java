/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculumDTO;
import com.kyriba.curriculum.domain.dto.CourseDTO;
import com.kyriba.curriculum.domain.dto.CourseToAddDTO;
import com.kyriba.curriculum.domain.dto.CourseToUpdateDTO;
import com.kyriba.curriculum.domain.dto.CurriculumDTO;
import com.kyriba.curriculum.domain.dto.CurriculumToCreateDTO;
import com.kyriba.curriculum.service.CurriculumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.util.Collections;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/api/v1/curricula")
@Api
class CurriculumController
{
  private CurriculumService curriculumService;


  @Autowired
  CurriculumController(CurriculumService curriculumService)
  {
    this.curriculumService = curriculumService;
  }


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get curricula", notes = "Retrieving the collection of curricula", response = BriefCurriculumDTO.class,
      responseContainer = "List")
  List<BriefCurriculumDTO> getCurricula(@ApiParam("Grade") @RequestParam(value = "grade", required = false) Integer grade)
  {
    if (grade == null) {
      return curriculumService.findAllCurricula();
    }
    else {
      return Collections.singletonList(curriculumService.getCurriculumByGrade(grade).toBrief());
    }
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get curriculum by id", notes = "Retrieving existing curriculum by its identifier", response = CurriculumDTO.class)
  CurriculumDTO getCurriculumById(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long id)
  {
    return curriculumService.getCurriculumById(id);
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create curriculum", notes = "Create new curriculum", response = BriefCurriculumDTO.class)
  BriefCurriculumDTO createCurriculum(@ApiParam("New curriculum") @RequestBody CurriculumToCreateDTO curriculumToCreate)
  {
    return curriculumService.createCurriculum(curriculumToCreate);
  }


  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete curriculum", notes = "Delete existing curriculum", response = BriefCurriculumDTO.class)
  void removeCurriculum(@ApiParam("Identifier of curriculum to delete") @PathVariable("id") long curriculumId)
  {
    curriculumService.removeCurriculum(curriculumId);
  }


  @PostMapping(
      value = "/{id}/courses",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Add new course", notes = "Add new course to existing curriculum", response = CourseDTO.class)
  CourseDTO addCourse(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long curriculumId,
                      @ApiParam("New course") @RequestBody CourseToAddDTO courseToAdd)
  {
    return curriculumService.addCourse(curriculumId, courseToAdd);
  }


  @DeleteMapping(
      value = "/{curriculumId}/courses/{courseId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete course", notes = "Delete existing course", response = CourseDTO.class)
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
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update lesson count for course", notes = "Update lesson count for existing course", response = CourseDTO.class)
  void updateCourse(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId,
      @ApiParam("Updated course") @RequestBody CourseToUpdateDTO courseToUpdate)
  {
    curriculumService.updateCourse(curriculumId, courseId, courseToUpdate);
  }
}
