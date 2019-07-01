/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectToUpdateDTO;
import com.kyriba.curriculum.service.SubjectService;
import com.kyriba.curriculum.service.exception.CurriculumServiceException;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/api/v1/subjects")
@Api
class SubjectController
{
  private SubjectService subjectService;


  @Autowired
  SubjectController(SubjectService subjectService)
  {
    this.subjectService = subjectService;
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create subject", notes = "Creating new subject", response = SubjectDTO.class)
  SubjectDTO createSubject(@RequestBody @ApiParam("New subject") SubjectToCreateDTO subjectToCreate)
  {
    return subjectService.createSubject(subjectToCreate);
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update subject", notes = "Updating an existing subject", response = SubjectDTO.class)
  void updateSubject(
      @ApiParam("Subject id of the subject to be updated") @PathVariable("id") long subjectId,
      @ApiParam("Updated subject") @RequestBody SubjectToUpdateDTO subjectToUpdate)
  {
    subjectService.updateSubject(subjectId, subjectToUpdate);
  }


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all subjects", notes = "Retrieving the collection of subjects",
      response = SubjectDTO.class, responseContainer = "List")
  List<SubjectDTO> getAllSubjects()
  {
    return subjectService.getAllSubjects();
  }


  @ControllerAdvice(assignableTypes = SubjectController.class)
  static class ControllerExceptionHandler
  {
    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorMessage> handleNotFoundException(CurriculumServiceException e)
    {
      return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SubjectAlreadyExistsException.class)
    @ResponseBody
    ResponseEntity<ErrorMessage> handleConflictException(CurriculumServiceException e)
    {

      return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.CONFLICT);
    }
  }
}
