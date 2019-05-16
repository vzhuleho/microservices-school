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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
@Service
@Profile({"default", "prod"})
class CurriculumServiceImpl implements CurriculumService
{
  @Override
  public Optional<Curriculum> findCurriculumById(long id)
  {
    return Optional.empty();
  }


  @Override
  public Optional<Curriculum> findCurriculumByGrade(int grade)
  {
    return Optional.empty();
  }


  @Override
  public List<BriefCurriculum> getAllCurricula()
  {
    return Collections.emptyList();
  }


  @Override
  public List<Subject> getAllSubjects()
  {
    return Collections.emptyList();
  }


  @Override
  public BriefCurriculum createCurriculum(int grade)
  {
    return null;
  }


  @Override
  public Course addCourse(long curriculumId, CourseToAdd course)
  {
    return null;
  }


  @Override
  public BriefCurriculum removeCurriculum(long curriculumId)
  {
    return null;
  }


  @Override
  public Course updateCourse(long curriculumId, long courseId, int lessonCount)
  {
    return null;
  }


  @Override
  public Course removeCourse(long curriculumId, long courseId)
  {
    return null;
  }


  @Override
  public Subject createSubject(String name)
  {
    return null;
  }
}
