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
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author M-DBE
 */
@Service
@AllArgsConstructor
@Primary
public class ExceptionHandlingCurriculumServiceDecorator implements CurriculumService
{
  private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

  @Qualifier("main")
  private final CurriculumService curriculumService;


  @Override
  public List<BriefCurriculumDTO> findAllCurricula()
  {
    return curriculumService.findAllCurricula();
  }


  @Override
  public CurriculumDTO getCurriculumByGrade(int grade)
  {
    return curriculumService.getCurriculumByGrade(grade);
  }


  @Override
  public CurriculumDTO getCurriculumById(long id)
  {
    return curriculumService.getCurriculumById(id);
  }


  @Override
  public BriefCurriculumDTO createCurriculum(CurriculumToCreateDTO curriculumToCreate)
  {
    try {
      return curriculumService.createCurriculum(curriculumToCreate);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Curriculum with grade %d can't be created!", curriculumToCreate.getGrade()), e);
      throw new CurriculumAlreadyExistsException(curriculumToCreate.getGrade());
    }
  }


  @Override
  public void removeCurriculum(long curriculumId)
  {
    try {
      curriculumService.removeCurriculum(curriculumId);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Curriculum with id %d doesn't exist!", curriculumId), e);
      throw new CurriculumNotFoundException(curriculumId);
    }
  }


  @Override
  public CourseDTO addCourse(long curriculumId, CourseToAddDTO courseToAdd)
  {
    try {
      return curriculumService.addCourse(curriculumId, courseToAdd);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Course already exists for curriculum %d and subject %d!",
          curriculumId, courseToAdd.getSubjectId()), e);
      throw new CourseAlreadyExistsException(curriculumId, courseToAdd.getSubjectId());
    }
  }


  @Override
  public void removeCourse(long curriculumId, long courseId)
  {
    curriculumService.removeCourse(curriculumId, courseId);
  }


  @Override
  public void updateCourse(long curriculumId, long courseId, CourseToUpdateDTO courseToUpdate)
  {
    try {
      curriculumService.updateCourse(curriculumId, courseId, courseToUpdate);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Course already exists for curriculum %d and subject %d!",
          curriculumId, courseToUpdate.getSubjectId()), e);
      throw new CourseAlreadyExistsException(curriculumId, courseToUpdate.getSubjectId());
    }
  }
}
