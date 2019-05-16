/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.api.dto.BriefCurriculum;
import com.kyriba.curriculum.api.dto.Course;
import com.kyriba.curriculum.api.dto.Curriculum;
import com.kyriba.curriculum.api.dto.Subject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author M-DBE
 */
@Service
@Profile("!default & !prod")
class NonProdCurriculumServiceImpl implements CurriculumService
{
  private static final List<Curriculum> CURRICULA = Arrays.asList(
      new Curriculum(1, 11, Arrays.asList(
          new Course(100, Subjects.ALGEBRA, 100),
          new Course(101, Subjects.GEOMETRY, 100),
          new Course(102, Subjects.ENGLISH, 100))
      ),
      new Curriculum(2, 10, Arrays.asList(
          new Course(103, Subjects.ALGEBRA, 110),
          new Course(104, Subjects.GEOMETRY, 90),
          new Course(105, Subjects.ENGLISH, 80))
      )
  );


  @Override
  public List<BriefCurriculum> getAllCurricula()
  {
    return CURRICULA.stream()
        .map(curriculum -> new BriefCurriculum(curriculum.getId(), curriculum.getGrade()))
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


  private static class Subjects
  {
    static Subject ALGEBRA = new Subject(1000, "algebra");
    static Subject GEOMETRY = new Subject(1001, "geometry");
    static Subject ENGLISH = new Subject(1002, "english");
  }
}
