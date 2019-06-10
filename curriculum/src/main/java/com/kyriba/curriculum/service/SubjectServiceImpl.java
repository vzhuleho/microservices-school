/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import com.kyriba.curriculum.domain.entity.SubjectEntity;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author M-DBE
 */
@Service
@Profile("!test")
@Transactional
class SubjectServiceImpl implements SubjectService
{
  private final SubjectRepository repository;


  @Autowired
  SubjectServiceImpl(SubjectRepository repository)
  {
    this.repository = repository;
  }


  @Override
  public Subject createSubject(SubjectToCreate subjectToCreate)
  {
    SubjectEntity savedEntity = repository.save(new SubjectEntity(subjectToCreate.getName()));
    return new Subject(savedEntity.getId(), savedEntity.getName());
  }


  @Override
  public void updateSubject(long subjectId, SubjectToUpdate subjectToUpdate)
  {
    SubjectEntity entity = repository.findById(subjectId)
        .orElseThrow(() -> new SubjectNotFoundException(subjectId));
    entity.setName(subjectToUpdate.getName());
    repository.save(entity);
  }


  @Override
  public List<Subject> getAllSubjects()
  {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(entity -> new Subject(entity.getId(), entity.getName()))
        .collect(Collectors.toList());
  }
}
