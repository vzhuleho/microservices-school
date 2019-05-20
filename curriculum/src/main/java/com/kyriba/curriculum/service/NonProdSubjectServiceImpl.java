/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.kyriba.curriculum.service.NonProdData.COUNTER;
import static com.kyriba.curriculum.service.NonProdData.SUBJECTS;


/**
 * @author M-DBE
 */
@Service
@Profile("!default & !prod")
@RequiredArgsConstructor
public class NonProdSubjectServiceImpl implements SubjectService
{
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
  public Optional<Subject> updateSubject(long subjectId, SubjectToUpdate subjectToUpdate)
  {
    return SUBJECTS.stream()
        .filter(it -> it.getId() == subjectId)
        .findFirst()
        .map(existingSubject -> new Subject(existingSubject.getId(), subjectToUpdate.getName()));
  }


  @Override
  public List<Subject> getAllSubjects()
  {
    return SUBJECTS;
  }


  @Override
  public Optional<Subject> getSubjectById(long subjectId)
  {
    return SUBJECTS.stream()
        .filter(subject -> subject.getId() == subjectId).findFirst();
  }
}
