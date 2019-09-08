/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;


/**
 * @author M-DBE
 */
@Aspect
@Component
class SubjectServiceExceptionHandlingAspect
{
  private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);


  @AfterThrowing(
      throwing = "e",
      pointcut = "execution(* com.kyriba.curriculum.service.SubjectServiceImpl.createSubject(..))"
  )
  void handleCreateSubject(JoinPoint joinPoint, DataIntegrityViolationException e)
  {
    var dto = (SubjectToCreateDTO) joinPoint.getArgs()[0];
    logger.error(String.format("Subject with name %s can't be created!", dto.getName()), e);
    throw new SubjectAlreadyExistsException(dto.getName());
  }
}
