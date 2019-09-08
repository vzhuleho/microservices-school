/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.CourseToAddDTO;
import com.kyriba.curriculum.domain.dto.CourseToUpdateDTO;
import com.kyriba.curriculum.domain.dto.CurriculumToCreateDTO;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;


/**
 * @author M-DBE
 */
@Aspect
@Component
class CurriculumServiceExceptionHandlingAspect
{
  private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);


  @AfterThrowing(
      throwing = "e",
      pointcut = "execution(* com.kyriba.curriculum.service.CurriculumServiceImpl.removeCurriculum(..))"
  )
  void handleRemoveCurriculum(JoinPoint joinPoint, EmptyResultDataAccessException e)
  {
    var curriculumId = (long) joinPoint.getArgs()[0];
    logger.error(String.format("Curriculum with id %d doesn't exist!", curriculumId), e);
    throw new CurriculumNotFoundException(curriculumId);
  }


  @AfterThrowing(
      throwing = "e",
      pointcut = "execution(* com.kyriba.curriculum.service.CurriculumServiceImpl.createCurriculum(..))"
  )
  void handleCreateCurriculum(JoinPoint joinPoint, DataIntegrityViolationException e)
  {
    var dto = (CurriculumToCreateDTO) joinPoint.getArgs()[0];
    logger.error(String.format("Curriculum with grade %d can't be created!", dto.getGrade()), e);
    throw new CurriculumAlreadyExistsException(dto.getGrade());
  }


  @AfterThrowing(
      throwing = "e",
      pointcut = "execution(* com.kyriba.curriculum.service.CurriculumServiceImpl.addCourse(..))"
  )
  void handleAddCourse(JoinPoint joinPoint, DataIntegrityViolationException e)
  {
    var curriculumId = (long) joinPoint.getArgs()[0];
    var dto = (CourseToAddDTO) joinPoint.getArgs()[1];
    logger.error(String.format("Course already exists for curriculum %d and subject %d!",
        curriculumId, dto.getSubjectId()), e);
    throw new CourseAlreadyExistsException(curriculumId, dto.getSubjectId());
  }


  @AfterThrowing(
      throwing = "e",
      pointcut = "execution(* com.kyriba.curriculum.service.CurriculumServiceImpl.updateCourse(..))"
  )
  void handleUpdateCourse(JoinPoint joinPoint, DataIntegrityViolationException e)
  {
    var curriculumId = (long) joinPoint.getArgs()[0];
    var dto = (CourseToUpdateDTO) joinPoint.getArgs()[2];
    logger.error(String.format("Course already exists for curriculum %d and subject %d!",
        curriculumId, dto.getSubjectId()), e);
    throw new CourseAlreadyExistsException(curriculumId, dto.getSubjectId());
  }
}
