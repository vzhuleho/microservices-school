/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
public interface CurriculumService
{
  List<BriefCurriculum> findAllCurricula();


  Optional<Curriculum> findCurriculumByGrade(@GradeConstraint @Null Integer grade);


  Curriculum getCurriculumById(long id);


  BriefCurriculum createCurriculum(@Valid CurriculumToCreate curriculumToCreate);


  void removeCurriculum(long curriculumId);


  Course addCourse(long curriculumId, @Valid CourseToAdd courseToAdd);


  void removeCourse(long curriculumId, long courseId);


  void updateCourse(long curriculumId, long courseId, @Valid CourseToUpdate courseToUpdate);
}
