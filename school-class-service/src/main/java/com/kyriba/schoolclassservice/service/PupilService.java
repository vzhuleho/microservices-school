/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 26.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.PupilRepository;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.exceptions.PupilNotFoundException;
import com.kyriba.schoolclassservice.service.exceptions.SchoolClassNotFoundException;
import com.kyriba.schoolclassservice.service.externalservices.PupilServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
@Validated
@RequiredArgsConstructor
public class PupilService
{
  private final SchoolClassRepository classRepository;
  private final PupilRepository pupilRepository;
  private final PupilServiceClient pupilServiceClient;


  @Transactional
  public PupilDto addPupilToClass(Long classId, @Valid PupilDto pupil)
  {
    final SchoolClassEntity schoolClassEntity = classRepository.findById(classId)
        .orElseThrow(() -> new SchoolClassNotFoundException(classId));

    final PupilEntity newPupil = pupilServiceClient.findById(pupil)
        .orElseThrow(() -> new PupilNotFoundException(pupil.getId()));

    newPupil.setSchoolClass(schoolClassEntity);
    pupilRepository.save(newPupil);

    return pupil;
  }


  @Transactional
  public void removePupilFromClass(Long classId, Long pupilId)
  {
    final PupilEntity pupilEntity = pupilRepository.findById(pupilId)
        .orElseThrow(() -> new PupilNotFoundException(pupilId));
    pupilEntity.setSchoolClass(null);
    pupilRepository.save(pupilEntity);
  }

}
