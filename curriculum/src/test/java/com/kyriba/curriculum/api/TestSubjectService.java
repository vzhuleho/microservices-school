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
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;


/**
 * @author M-DBE
 */
@Service
@Profile("test")
class TestSubjectService implements SubjectService
{
  static Subject ALGEBRA = new Subject(1000, "algebra");
  static Subject GEOMETRY = new Subject(1001, "geometry");
  static Subject ENGLISH = new Subject(1002, "english");
  private static Subject PHYSICS = new Subject(1003, "physics");

  static final Supplier<List<Subject>> DEFAULT_SUBJECTS = () -> new ArrayList<>(
      Arrays.asList(ALGEBRA, GEOMETRY, ENGLISH, PHYSICS));
  static List<Subject> SUBJECTS = DEFAULT_SUBJECTS.get();

  private static final AtomicLong COUNTER = new AtomicLong(10_000);


  @Override
  public Subject createSubject(SubjectToCreate subjectToCreate)
  {
    String subjectName = subjectToCreate.getName().trim();
    if (SUBJECTS.stream().anyMatch(it -> it.getName().equals(subjectName))) {
      throw new SubjectAlreadyExistsException(subjectName);
    }

    Subject newSubject = new Subject(COUNTER.incrementAndGet(), subjectName);
    SUBJECTS.add(newSubject);
    return newSubject;
  }


  @Override
  public void updateSubject(long subjectId, SubjectToUpdate subjectToUpdate)
  {
    SUBJECTS.stream()
        .filter(it -> it.getId() == subjectId)
        .findFirst()
        .map(existingSubject -> new Subject(existingSubject.getId(), subjectToUpdate.getName()))
        .orElseThrow(() -> new SubjectNotFoundException(subjectId));
  }


  @Override
  public List<Subject> getAllSubjects()
  {
    return SUBJECTS;
  }
}
