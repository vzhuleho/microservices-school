/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.api.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.api.exception.CourseNotFoundException;
import com.kyriba.curriculum.api.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.api.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.api.exception.CurriculumServiceException;
import com.kyriba.curriculum.api.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.api.exception.SubjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author M-DBE
 */
@ControllerAdvice(assignableTypes = { CurriculumController.class, SubjectController.class })
class CustomExceptionHandler
{
  @ExceptionHandler({ SubjectNotFoundException.class, CurriculumNotFoundException.class,
      CourseNotFoundException.class })
  @ResponseBody
  ResponseEntity<String> handleNotFoundException(CurriculumServiceException e)
  {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler({ CourseAlreadyExistsException.class, CurriculumAlreadyExistsException.class,
      SubjectAlreadyExistsException.class })
  @ResponseBody
  ResponseEntity<String> handleConflictException(CurriculumServiceException e)
  {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }
}
