/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
@Service
@Profile({ "default", "prod" })
public class SubjectServiceImpl implements SubjectService
{
  @Override
  public List<Subject> getAllSubjects()
  {
    return Collections.emptyList();
  }


  @Override
  public Subject createSubject(SubjectToCreate subjectToCreate)
  {
    return null;
  }


  @Override
  public Optional<Subject> updateSubject(long subjectId, SubjectToUpdate subjectToUpdate)
  {
    return Optional.empty();
  }


  @Override
  public Optional<Subject> getSubjectById(long subjectId)
  {
    return Optional.empty();
  }
}
