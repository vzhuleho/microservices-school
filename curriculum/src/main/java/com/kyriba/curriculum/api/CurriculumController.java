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
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.service.CurriculumService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/curricula")
@RequiredArgsConstructor
public class CurriculumController
{
  private final CurriculumService curriculumService;


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public List<BriefCurriculum> getAllCurricula()
  {
    return curriculumService.getAllCurricula();
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Curriculum getCurriculumById(@PathVariable("id") long id)
  {
    return curriculumService.findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @GetMapping(value = "/grades/{grade}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Curriculum getCurriculumByGrade(@Min(1) @Max(11) @PathVariable("grade") int grade)
  {
    return curriculumService.findCurriculumByGrade(grade)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED));
  }


  @GetMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public List<Subject> getAllSubjects()
  {
    return curriculumService.getAllSubjects();
  }


  @PostMapping(value = "/grades/{grade}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public BriefCurriculum createCurriculum(@Min(1) @Max(11) @PathVariable("grade") int grade)
  {
    return curriculumService.createCurriculum(grade);
  }


  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public BriefCurriculum removeCurriculum(@PathVariable("id") long curriculumId)
  {
    return curriculumService.removeCurriculum(curriculumId);
  }


  @PostMapping(
      value = "/{id}/courses",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  public Course addCourse(@PathVariable("id") long curriculumId,
                          @RequestBody CourseToAdd course)
  {
    return curriculumService.addCourse(curriculumId, course);
  }


  @DeleteMapping(
      value = "/{curriculumId}/courses/{courseId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  public Course removeCourse(@PathVariable("curriculumId") long curriculumId,
                             @PathVariable("courseId") long courseId)
  {
    return curriculumService.removeCourse(curriculumId, courseId);
  }


  @PutMapping(
      value = "/{curriculumId}/courses/{courseId}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  public Course updateCourse(@PathVariable("curriculumId") long curriculumId,
                             @PathVariable("courseId") long courseId,
                             @RequestParam("lessonCount") int lessonCount)
  {
    return curriculumService.updateCourse(curriculumId, courseId, lessonCount);
  }


  @PostMapping(value = "/subjects/{name}")
  @ResponseBody
  public Subject createSubject(@PathVariable("name") String name)
  {
    return curriculumService.createSubject(name);
  }
}
