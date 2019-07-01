/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectToUpdateDTO;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author M-DBE
 */
@Service
@AllArgsConstructor
@Primary
public class ExceptionHandlingSubjectServiceDecorator implements SubjectService
{
  private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

  @Qualifier("main")
  private final SubjectService subjectService;


  @Override
  public SubjectDTO createSubject(SubjectToCreateDTO subjectToCreate)
  {
    try {
      return subjectService.createSubject(subjectToCreate);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Subject with name %s can't be created!", subjectToCreate.getName()), e);
      throw new SubjectAlreadyExistsException(subjectToCreate.getName());
    }
  }


  @Override
  public void updateSubject(long subjectId, SubjectToUpdateDTO subjectToUpdate)
  {
    subjectService.updateSubject(subjectId, subjectToUpdate);
  }


  @Override
  public List<SubjectDTO> getAllSubjects()
  {
    return subjectService.getAllSubjects();
  }
}
