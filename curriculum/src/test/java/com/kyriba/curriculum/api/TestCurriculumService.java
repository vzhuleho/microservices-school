/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.google.common.collect.Lists;
import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.service.CurriculumService;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.kyriba.curriculum.api.TestSubjectService.ALGEBRA;
import static com.kyriba.curriculum.api.TestSubjectService.ENGLISH;
import static com.kyriba.curriculum.api.TestSubjectService.GEOMETRY;
import static com.kyriba.curriculum.api.TestSubjectService.SUBJECTS;


/**
 * @author M-DBE
 */
@Service
@Profile("test")
class TestCurriculumService implements CurriculumService
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


  @Override
  public List<BriefCurriculum> findAllCurricula()
  {
    return CURRICULA.stream()
        .map(Curriculum::toBrief)
        .collect(Collectors.toList());
  }


  @Override
  public Optional<Curriculum> findCurriculumByGrade(Integer grade)
  {
    return CURRICULA.stream()
        .filter(it -> it.getGrade() == grade)
        .findFirst();
  }


  @Override
  public Curriculum getCurriculumById(long id)
  {
    return findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @Override
  public BriefCurriculum createCurriculum(CurriculumToCreate curriculumToCreate)
  {
    if (findCurriculumByGrade(curriculumToCreate.getGrade()).isPresent()) {
      throw new CurriculumAlreadyExistsException(curriculumToCreate.getGrade());
    }

    Curriculum curriculum = new Curriculum(COUNTER.incrementAndGet(), curriculumToCreate.getGrade(),
        Collections.emptyList());
    CURRICULA.add(curriculum);

    return curriculum.toBrief();
  }


  @Override
  public void removeCurriculum(long curriculumId)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    boolean removed = CURRICULA.remove(curriculum);
    if (!removed)
      throw new CurriculumNotFoundException(curriculumId);
  }


  private Optional<Curriculum> findCurriculumById(long id)
  {
    return CURRICULA.stream()
        .filter(it -> it.getId() == id).findFirst();
  }


  @Override
  public Course addCourse(long curriculumId, CourseToAdd courseToAdd)
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


  @Override
  public void removeCourse(long curriculumId, long courseId)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    Course course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));
    curriculum.getCourses().remove(course);
  }


  @Override
  public void updateCourse(long curriculumId, long courseId, CourseToUpdate courseToUpdate)
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
