/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 11.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import com.kyriba.schoolclassservice.service.exceptions.SchoolClassNotFoundException;
import com.kyriba.schoolclassservice.service.exceptions.TeacherNotFoundException;
import com.kyriba.schoolclassservice.service.externalservices.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
@Validated
@RequiredArgsConstructor
public class SchoolClassService
{

  private final SchoolClassRepository classRepository;
  private final UserDataService userDataService;


  public List<SchoolClassDto> getAll()
  {
    return classRepository.findAll().stream()
        .map(SchoolClassEntity::toDto).collect(Collectors.toList());
  }


  public SchoolClassDto getById(Long id)
  {
    return classRepository.findById(id)
        .orElseThrow(() -> new SchoolClassNotFoundException(id))
        .toDto();
  }


  @Transactional
  public SchoolClassDto create(@Valid SchoolClassDto schoolClass)
  {
    final SchoolClassEntity classEntity = new SchoolClassEntity().populateFrom(schoolClass);
    if (schoolClass.getHeadTeacher() != null) {
      HeadTeacherEntity teacher = userDataService.findById(schoolClass.getHeadTeacher())
          .orElseThrow(() -> new TeacherNotFoundException(schoolClass.getHeadTeacher().getId()));
      classEntity.setHeadTeacherEntity(teacher);
    }
    return classRepository.save(classEntity).toDto();
  }



  @Transactional
  public SchoolClassDto updateClass(Long classId, @Valid ClassUpdateRequest updateRequest)
  {
    SchoolClassEntity schoolClassEntity = classRepository.findById(classId)
        .orElseThrow(() -> new SchoolClassNotFoundException(classId));
    schoolClassEntity.populateFrom(updateRequest);
    if (updateRequest.getHeadTeacher() != null) {
      HeadTeacherEntity teacher = userDataService.findById(updateRequest.getHeadTeacher())
          .orElseThrow(() -> new TeacherNotFoundException(updateRequest.getHeadTeacher().getId()));
      schoolClassEntity.setHeadTeacherEntity(teacher);
    }
    return classRepository.save(schoolClassEntity).toDto();
  }


  @Transactional
  public void deleteClass(Long classId)
  {
    classRepository.deleteById(classId);
  }


  public Set<PupilDto> getPupilsForClass(Long classId)
  {
    Set<PupilEntity> pupilEntities = classRepository.findById(classId)
        .map(SchoolClassEntity::getPupils)
        .orElseThrow(() -> new SchoolClassNotFoundException(classId));

    return pupilEntities.stream().map(PupilEntity::toDto).collect(Collectors.toSet());
  }


}
