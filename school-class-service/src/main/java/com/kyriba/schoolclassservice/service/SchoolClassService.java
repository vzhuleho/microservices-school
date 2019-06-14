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
import com.kyriba.schoolclassservice.repository.PupilRepository;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.repository.TeacherRepository;
import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
@RequiredArgsConstructor
public class SchoolClassService {

  private final SchoolClassRepository classRepository;
  private final TeacherRepository headTeacherRepository;
  private final PupilRepository pupilRepository;

  public List<SchoolClassDto> getAll() {
    return classRepository.findAll().stream().map(SchoolClassDto::of).collect(Collectors.toList());
  }


  public SchoolClassDto getById(Long id) {
    SchoolClassEntity byId = classRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    return SchoolClassDto.of(byId);
  }


  @Transactional
  public SchoolClassDto create(@Valid SchoolClassDto schoolClass) {
    HeadTeacherEntity teacher;
    if (schoolClass.getHeadTeacher() != null) {
      teacher = headTeacherRepository.findById(schoolClass.getHeadTeacher().getId())
          .orElseThrow(() -> new ResourceNotFoundException("Specified teacher is not exists"));
    }
    return SchoolClassDto.of(classRepository.save(schoolClass.toEntity()));
  }


  @Transactional
  public SchoolClassDto updateClass(Long classId, @Valid ClassUpdateRequest updateRequest) {
    headTeacherRepository
    SchoolClassEntity byId = classRepository.findById(classId).orElseThrow(ResourceNotFoundException::new);
    return SchoolClassDto.of(classRepository.save(updateRequest.toEntity(byId)));
  }


  @Transactional
  public void deleteClass(Long classId) {
    classRepository.deleteById(classId);
  }


  public Set<PupilDto> getPupilsForClass(Long classId) {
    Set<PupilEntity> pupilEntities = classRepository.findById(classId)
        .map(SchoolClassEntity::getPupils)
        .orElseThrow(ResourceNotFoundException::new);

    return pupilEntities.stream().map(PupilDto::of).collect(Collectors.toSet());
  }

  @Transactional
  public PupilDto addPupilToClass(Long classId, @Valid PupilDto pupil) {
    //todo: verify pupil exists in user service
    //todo: verify pupil is not registered to some class
    SchoolClassEntity schoolClassEntity = classRepository.findById(classId)
        .orElseThrow(ResourceNotFoundException::new);

    PupilEntity entity = pupil.toEntity();
    entity.setSchoolClass(schoolClassEntity);
    pupilRepository.save(entity);

    return pupil;
  }

  @Transactional
  public void removePupilFromClass(Long classId, Long pupilId) {

  }
}
