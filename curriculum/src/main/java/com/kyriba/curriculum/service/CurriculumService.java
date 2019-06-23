/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculumDTO;
import com.kyriba.curriculum.domain.dto.CourseDTO;
import com.kyriba.curriculum.domain.dto.CourseToAddDTO;
import com.kyriba.curriculum.domain.dto.CourseToUpdateDTO;
import com.kyriba.curriculum.domain.dto.CurriculumDTO;
import com.kyriba.curriculum.domain.dto.CurriculumToCreateDTO;
import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;

import javax.validation.Valid;
import java.util.List;


/**
 * @author M-DBE
 */
public interface CurriculumService
{
  List<BriefCurriculumDTO> findAllCurricula();


  CurriculumDTO getCurriculumByGrade(@GradeConstraint int grade);


  CurriculumDTO getCurriculumById(long id);


  BriefCurriculumDTO createCurriculum(@Valid CurriculumToCreateDTO curriculumToCreate);


  void removeCurriculum(long curriculumId);


  CourseDTO addCourse(long curriculumId, @Valid CourseToAddDTO courseToAdd);


  void removeCourse(long curriculumId, long courseId);


  void updateCourse(long curriculumId, long courseId, @Valid CourseToUpdateDTO courseToUpdate);
}
