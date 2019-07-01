/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectToUpdateDTO;

import javax.validation.Valid;
import java.util.List;


/**
 * @author M-DBE
 */
public interface SubjectService
{
  SubjectDTO createSubject(@Valid SubjectToCreateDTO subjectToCreate);


  void updateSubject(long subjectId, @Valid SubjectToUpdateDTO subjectToUpdate);


  List<SubjectDTO> getAllSubjects();
}
