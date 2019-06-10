/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import com.kyriba.curriculum.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@Validated
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
  @ApiOperation(value = "Create subject", notes = "Creating new subject", response = Subject.class)
  Subject createSubject(@Valid @RequestBody @ApiParam("New subject") SubjectToCreate subjectToCreate)
  {
    return subjectService.createSubject(subjectToCreate);
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update subject", notes = "Updating an existing subject", response = Subject.class)
  void updateSubject(
      @ApiParam("Subject id of the subject to be updated") @PathVariable("id") long subjectId,
      @ApiParam("Updated subject") @Valid @RequestBody SubjectToUpdate subjectToUpdate)
  {
    subjectService.updateSubject(subjectId, subjectToUpdate);
  }


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all subjects", notes = "Retrieving the collection of subjects",
      response = Subject.class, responseContainer = "List")
  List<Subject> getAllSubjects()
  {
    return subjectService.getAllSubjects();
  }
}
