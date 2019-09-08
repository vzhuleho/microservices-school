/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectToUpdateDTO;
import com.kyriba.curriculum.domain.entity.SubjectEntity;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author M-DBE
 */
@Service
@Transactional
@Validated
public class SubjectServiceImpl implements SubjectService
{
  private final SubjectRepository repository;


  @Autowired
  public SubjectServiceImpl(SubjectRepository repository)
  {
    this.repository = repository;
  }


  @Override
  public SubjectDTO createSubject(SubjectToCreateDTO subjectToCreate)
  {
    return repository.save(new SubjectEntity(subjectToCreate.getName())).toSubjectDTO();
  }


  @Override
  public void updateSubject(long subjectId, SubjectToUpdateDTO subjectToUpdate)
  {
    var entity = repository.findById(subjectId)
        .orElseThrow(() -> new SubjectNotFoundException(subjectId));
    entity.setName(subjectToUpdate.getName());
    repository.save(entity);
  }


  @Override
  public List<SubjectDTO> getAllSubjects()
  {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(SubjectEntity::toSubjectDTO)
        .collect(Collectors.toList());
  }
}
