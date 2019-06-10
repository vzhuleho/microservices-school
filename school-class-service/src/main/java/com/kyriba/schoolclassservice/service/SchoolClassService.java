/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 11.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
public class SchoolClassService {

  @Autowired
  private SchoolClassRepository repository;

  public List<SchoolClassDto> getAll() {
    return repository.findAll().stream().map(SchoolClassDto::of).collect(Collectors.toList());
  }


  public SchoolClassDto getById(Long id) {
    SchoolClassEntity byId = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    return SchoolClassDto.of(byId);
  }


  @Transactional
  public SchoolClassDto create(SchoolClassDto schoolClass) {
    return SchoolClassDto.of(repository.save(schoolClass.toEntity()));
  }


  @Transactional
  public SchoolClassDto updateClass(Long classId, ClassUpdateRequest updateRequest) {
    SchoolClassEntity byId = repository.findById(classId).orElseThrow(EntityNotFoundException::new);
    return SchoolClassDto.of(repository.save(updateRequest.toEntity(byId)));
  }


  @Transactional
  public void deleteClass(Long classId) {
    repository.deleteById(classId);
  }


  //todo: pupils
  public Set<PupilDto> getPupilsForClass(Long classId) {
    return Collections.emptySet();
  }


  public PupilDto addPupilToClass(Long classId, PupilDto pupil) {
    return pupil;
  }


  public void removePupilFromClass(Long classId, Long pupilId) {

  }
}
