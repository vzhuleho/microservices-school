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
import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
@RequiredArgsConstructor
public class SchoolClassService
{

  private final SchoolClassRepository classRepository;
  private final TeacherRepository headTeacherRepository;
  private final PupilRepository pupilRepository;

  //External services client
  private final UserServiceClient userServiceClient;


  public List<SchoolClassDto> getAll()
  {
    return classRepository.findAll().stream().map(SchoolClassDto::of).collect(Collectors.toList());
  }


  public SchoolClassDto getById(Long id)
  {
    SchoolClassEntity byId = classRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    return SchoolClassDto.of(byId);
  }


  @Transactional
  public SchoolClassDto create(@Valid SchoolClassDto schoolClass)
  {
    final SchoolClassEntity classEntity = schoolClass.toEntity();
    HeadTeacherEntity teacher = readOrCreateTeacher(schoolClass.getHeadTeacher());
    classEntity.setHeadTeacherEntity(teacher);
    return SchoolClassDto.of(classRepository.save(classEntity));
  }


  @Nullable
  private HeadTeacherEntity readOrCreateTeacher(@Valid final HeadTeacherDto headTeacherDto)
  {
    HeadTeacherEntity teacher = null;
    if (headTeacherDto != null) {
      //is it exists in the local storage?
      final Long teacherId = headTeacherDto.getId();
      final Optional<HeadTeacherEntity> teacherLocalStorage = headTeacherRepository.findById(
          teacherId);
      if (!teacherLocalStorage.isPresent()) {
        final HeadTeacherEntity headTeacherEntity = userServiceClient.findById(headTeacherDto)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher with " + teacherId + " is not found!"))
            .toEntity();

        teacher = headTeacherRepository.save(headTeacherEntity);
      }
      else {
        teacher = teacherLocalStorage.get();
      }
    }
    return teacher;
  }


  @Transactional
  public SchoolClassDto updateClass(Long classId, @Valid ClassUpdateRequest updateRequest)
  {
    SchoolClassEntity byId = classRepository.findById(classId).orElseThrow(ResourceNotFoundException::new);
    HeadTeacherEntity teacher = readOrCreateTeacher(updateRequest.getHeadTeacher());
    final SchoolClassEntity schoolClassEntity = updateRequest.toEntity(byId);
    schoolClassEntity.setHeadTeacherEntity(teacher);
    return SchoolClassDto.of(classRepository.save(schoolClassEntity));
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
        .orElseThrow(ResourceNotFoundException::new);

    return pupilEntities.stream().map(PupilDto::of).collect(Collectors.toSet());
  }


  @Transactional
  public PupilDto addPupilToClass(Long classId, @Valid PupilDto pupil)
  {
    SchoolClassEntity schoolClassEntity = classRepository.findById(classId)
        .orElseThrow(ResourceNotFoundException::new);

    final PupilEntity newPupil = readPupil(pupil);
    newPupil.setSchoolClass(schoolClassEntity);
    pupilRepository.save(newPupil);

    return pupil;
  }


  @Transactional
  public void removePupilFromClass(Long classId, Long pupilId)
  {
    final PupilEntity pupilEntity = pupilRepository.findById(pupilId).orElseThrow(ResourceNotFoundException::new);
    pupilEntity.setSchoolClass(null);
    pupilRepository.save(pupilEntity);
  }


  @Nullable
  private PupilEntity readPupil(@Valid final PupilDto pupilDto)
  {
    PupilEntity result = null;
    if (pupilDto != null) {
      final Long id = pupilDto.getId();
      final Optional<PupilEntity> pupil = pupilRepository.findById(id);
      result = pupil.orElseGet(() -> userServiceClient.findById(pupilDto)
          .orElseThrow(() -> new ResourceNotFoundException("Pupil with " + id + " is not found!"))
          .toEntity());
    }
    return result;
  }
}
