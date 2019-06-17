/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;

import javax.validation.Valid;
import java.util.List;


/**
 * @author M-DBE
 */
public interface SubjectService
{
  Subject createSubject(@Valid SubjectToCreate subjectToCreate);


  void updateSubject(long subjectId, @Valid SubjectToUpdate subjectToUpdate);


  List<Subject> getAllSubjects();
}
