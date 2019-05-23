/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.google.common.collect.Lists;
import com.kyriba.curriculum.api.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.api.exception.CourseNotFoundException;
import com.kyriba.curriculum.api.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.api.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.api.exception.SubjectNotFoundException;
import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.kyriba.curriculum.api.SubjectController.ALGEBRA;
import static com.kyriba.curriculum.api.SubjectController.ENGLISH;
import static com.kyriba.curriculum.api.SubjectController.GEOMETRY;
import static com.kyriba.curriculum.api.SubjectController.SUBJECTS;


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
  static final Supplier<List<Curriculum>> DEFAULT_CURRICULA = () -> Lists.newArrayList(
      new Curriculum(1, 11, Lists.newArrayList(
          new Course(100, ALGEBRA, 100),
          new Course(101, GEOMETRY, 100),
          new Course(102, ENGLISH, 100))
      ),
      new Curriculum(2, 10, Lists.newArrayList(
          new Course(103, ALGEBRA, 110),
          new Course(104, GEOMETRY, 90),
          new Course(105, ENGLISH, 80))
      )
  );
  static List<Curriculum> CURRICULA = DEFAULT_CURRICULA.get();

  private static final AtomicLong COUNTER = new AtomicLong(10_000);


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all curricula", notes = "Retrieving the collection of curricula", response = BriefCurriculum.class,
      responseContainer = "List")
  List<BriefCurriculum> getAllCurricula()
  {
    return CURRICULA.stream()
        .map(Curriculum::toBrief)
        .collect(Collectors.toList());
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get curriculum by id", notes = "Retrieving existing curriculum by its identifier", response = Curriculum.class)
  Curriculum getCurriculumById(@ApiParam("Identifier of existing curriculum") @PathVariable("id") long id)
  {
    return findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Find existing curriculum by grade", notes = "Retrieving existing curriculum by grade", response = Curriculum.class)
  Curriculum searchByParameters(@ApiParam("Grade") @GradeConstraint @RequestParam("grade") int grade)
  {
    return findCurriculumByGrade(grade)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED));
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create curriculum", notes = "Create new curriculum", response = BriefCurriculum.class)
  BriefCurriculum createCurriculum(
      @ApiParam("New curriculum") @Valid @RequestBody CurriculumToCreate curriculumToCreate)
  {
    if (findCurriculumByGrade(curriculumToCreate.getGrade()).isPresent()) {
      throw new CurriculumAlreadyExistsException(curriculumToCreate.getGrade());
    }

    Curriculum curriculum = new Curriculum(COUNTER.incrementAndGet(), curriculumToCreate.getGrade(),
        Collections.emptyList());
    CURRICULA.add(curriculum);

    return curriculum.toBrief();
  }


  private Optional<Curriculum> findCurriculumByGrade(int grade)
  {
    return CURRICULA.stream()
        .filter(it -> it.getGrade() == grade)
        .findFirst();
  }


  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Delete curriculum", notes = "Delete existing curriculum", response = BriefCurriculum.class)
  BriefCurriculum removeCurriculum(
      @ApiParam("Identifier of curriculum to delete") @PathVariable("id") long curriculumId)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    boolean removed = CURRICULA.remove(curriculum);
    if (!removed)
      throw new CurriculumNotFoundException(curriculumId);

    return curriculum.toBrief();
  }


  private Optional<Curriculum> findCurriculumById(long id)
  {
    return CURRICULA.stream()
        .filter(it -> it.getId() == id).findFirst();
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
                   @ApiParam("New course") @Valid @RequestBody CourseToAdd courseToAdd)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    Subject subject = getSubjectById(courseToAdd.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToAdd.getSubjectId()));

    if (curriculum.getCourses().stream()
        .anyMatch(it -> it.getSubject().equals(subject))) {
      throw new CourseAlreadyExistsException(curriculumId, courseToAdd.getSubjectId());
    }

    Course createdCourse = new Course(COUNTER.incrementAndGet(), subject, courseToAdd.getLessonCount());
    curriculum.getCourses().add(createdCourse);
    return createdCourse;
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
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    Course course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));
    curriculum.getCourses().remove(course);
  }


  @PutMapping(
      value = "/{curriculumId}/courses/{courseId}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseBody
  @ApiOperation(value = "Update lesson count for course", notes = "Update lesson count for existing course", response = Course.class)
  Course updateCourse(
      @ApiParam("Identifier of existing curriculum") @PathVariable("curriculumId") long curriculumId,
      @ApiParam("Identifier of existing course") @PathVariable("courseId") long courseId,
      @ApiParam("Updated course") @Valid @RequestBody CourseToUpdate courseToUpdate)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    Subject subject = getSubjectById(courseToUpdate.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToUpdate.getSubjectId()));

    if (curriculum.getCourses().stream()
        .anyMatch(it -> it.getSubject().equals(subject) && it.getId() != courseId))
      throw new CourseAlreadyExistsException(curriculumId, courseToUpdate.getSubjectId());

    Course updatedCourse = new Course(courseId, subject, courseToUpdate.getLessonCount());
    boolean removed = curriculum.getCourses().removeIf(it -> it.getId() == courseId);
    if (removed) {
      curriculum.getCourses().add(updatedCourse);
      return updatedCourse;
    }
    else {
      throw new CourseNotFoundException(curriculumId, courseId);
    }
  }


  private Optional<Subject> getSubjectById(long subjectId)
  {
    return SUBJECTS.stream()
        .filter(subject -> subject.getId() == subjectId).findFirst();
  }
}
