/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;


/**
 * @author M-DBE
 */
@ControllerAdvice
class ValidationExceptionHandler
{
  @ResponseBody
  @ExceptionHandler({ ValidationException.class, ConstraintViolationException.class })
  ResponseEntity<String> exceptionHandler(ValidationException e)
  {
    return new ResponseEntity<>(e.getMessage(), textPlain(), HttpStatus.BAD_REQUEST);
  }


  private static HttpHeaders textPlain()
  {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.TEXT_PLAIN);
    return httpHeaders;
  }
}
