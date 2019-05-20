/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;

import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
public interface SubjectService
{
  Subject createSubject(SubjectToCreate subjectToCreate);


  Optional<Subject> updateSubject(long subjectId, SubjectToUpdate subjectToUpdate);


  List<Subject> getAllSubjects();


  Optional<Subject> getSubjectById(long subjectId);
}
