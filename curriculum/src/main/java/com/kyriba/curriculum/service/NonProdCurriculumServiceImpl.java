/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kyriba.curriculum.service.NonProdData.COUNTER;
import static com.kyriba.curriculum.service.NonProdData.CURRICULA;
import static com.kyriba.curriculum.service.NonProdData.SUBJECTS;

/**
 * @author M-DBE
 */
@Service
@Profile("!default & !prod")
class NonProdCurriculumServiceImpl implements CurriculumService
{
  @Override
  public List<BriefCurriculum> getAllCurricula()
  {
    return CURRICULA.stream()
        .map(Curriculum::toBrief)
        .collect(Collectors.toList());
  }


  @Override
  public Optional<Curriculum> findCurriculumById(long id)
  {
    return CURRICULA.stream()
        .filter(it -> it.getId() == id).findFirst();
  }


  @Override
  public Optional<Curriculum> findCurriculumByGrade(int grade)
  {
    return CURRICULA.stream()
        .filter(it -> it.getGrade() == grade)
        .findFirst();
  }


  @Override
  public List<Subject> getAllSubjects()
  {
    return SUBJECTS;
  }


  @Override
  public BriefCurriculum createCurriculum(int grade)
  {
    if (findCurriculumByGrade(grade).isPresent()) {
      throw new CurriculumAlreadyExistsException();
    }

    Curriculum curriculum = new Curriculum(COUNTER.incrementAndGet(), grade, Collections.emptyList());
    CURRICULA.add(curriculum);

    return curriculum.toBrief();
  }


  @Override
  public BriefCurriculum removeCurriculum(long curriculumId)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    boolean removed = CURRICULA.remove(curriculum);
    if (!removed)
      throw new CurriculumNotFoundException(curriculumId);

    return curriculum.toBrief();
  }


  @Override
  public Course addCourse(long curriculumId, CourseToAdd courseToAdd)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));

    Subject subject = SUBJECTS.stream()
        .filter(it -> it.getId() == courseToAdd.getSubject().getId())
        .findFirst()
        .orElseThrow(() -> new SubjectNotFoundException(courseToAdd.getSubject().getId()));

    if (curriculum.getCourses().stream()
        .anyMatch(it -> it.getSubject().equals(subject))) {
      throw new CourseAlreadyExistsException(curriculumId, courseToAdd.getSubject().getId());
    }

    Course course = new Course(COUNTER.incrementAndGet(), subject, courseToAdd.getLessonCount());
    curriculum.getCourses().add(course);
    return course;
  }


  @Override
  public Course updateCourse(long curriculumId, long courseId, int lessonCount)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    Course course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));

    Course updatedCourse = new Course(course.getId(), course.getSubject(), lessonCount);
    curriculum.getCourses().remove(course);
    curriculum.getCourses().add(updatedCourse);

    return updatedCourse;
  }


  @Override
  public Course removeCourse(long curriculumId, long courseId)
  {
    Curriculum curriculum = findCurriculumById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    Course course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));
    curriculum.getCourses().remove(course);
    return course;
  }


  @Override
  public Subject createSubject(String name)
  {
    if (SUBJECTS.stream().anyMatch(it -> it.getName().equals(name))) {
      throw new SubjectAlreadyExistsException();
    }

    Subject newSubject = new Subject(COUNTER.incrementAndGet(), name);
    SUBJECTS.add(newSubject);
    return newSubject;
  }
}
