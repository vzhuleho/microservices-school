/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import com.kyriba.curriculum.api.exception.SubjectAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;


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
  static Subject ALGEBRA = new Subject(1000, "algebra");
  static Subject GEOMETRY = new Subject(1001, "geometry");
  static Subject ENGLISH = new Subject(1002, "english");
  static Subject PHYSICS = new Subject(1003, "physics");

  static final Supplier<List<Subject>> DEFAULT_SUBJECTS = () -> new ArrayList<>(
      Arrays.asList(ALGEBRA, GEOMETRY, ENGLISH, PHYSICS));
  static List<Subject> SUBJECTS = DEFAULT_SUBJECTS.get();

  private static final AtomicLong COUNTER = new AtomicLong(10_000);


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create subject", notes = "Creating new subject", response = Subject.class)
  Subject createSubject(@Valid @RequestBody @ApiParam("New subject") SubjectToCreate subjectToCreate)
  {
    String subjectName = subjectToCreate.getName().trim();
    if (SUBJECTS.stream().anyMatch(it -> it.getName().equals(subjectName))) {
      throw new SubjectAlreadyExistsException(subjectName);
    }

    Subject newSubject = new Subject(COUNTER.incrementAndGet(), subjectName);
    SUBJECTS.add(newSubject);
    return newSubject;
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Update subject", notes = "Updating an existing subject", response = Subject.class)
  ResponseEntity<Subject> updateSubject(
      @ApiParam("Subject id of the subject to be updated") @PathVariable("id") long subjectId,
      @ApiParam("Updated subject") @Valid @RequestBody SubjectToUpdate subjectToUpdate)
  {
    return SUBJECTS.stream()
      .filter(it -> it.getId() == subjectId)
      .findFirst()
      .map(existingSubject -> new Subject(existingSubject.getId(), subjectToUpdate.getName()))
        .map(updatedSubject -> new ResponseEntity<>(updatedSubject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(createSubject(new SubjectToCreate(subjectToUpdate.getName())),
            HttpStatus.CREATED));
  }


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @ApiOperation(value = "Get all subjects", notes = "Retrieving the collection of subjects",
      response = Subject.class, responseContainer = "List")
  List<Subject> getAllSubjects()
  {
    return SUBJECTS;
  }
}
