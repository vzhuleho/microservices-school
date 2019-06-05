/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 11.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * @author M-VBE
 * @since 19.2
 */
@Service
public class SchoolClassService
{
  public List<SchoolClassDto> getAll()
  {
    return Collections.emptyList();
  }


  public SchoolClassDto getById(Long id)
  {
    final SchoolClassDto schoolClassDto = new SchoolClassDto();
    schoolClassDto.setId(id);
    schoolClassDto.setLetter("A");
    schoolClassDto.setGrade(11);
    schoolClassDto.setYear(2013);
    return schoolClassDto;
  }


  public SchoolClassDto create(SchoolClassDto schoolClass)
  {
    schoolClass.setId(10L);
    schoolClass.setLetter("A");
    schoolClass.setGrade(11);
    schoolClass.setYear(2013);
    return schoolClass;
  }


  public SchoolClassDto updateClass(Long classId, ClassUpdateRequest updateRequest)
  {
    final SchoolClassDto schoolClassDto = new SchoolClassDto();
    schoolClassDto.setId(classId);
    schoolClassDto.setGrade(updateRequest.getGrade());
    schoolClassDto.setLetter(updateRequest.getLetter());
    schoolClassDto.setHeadTeacher(updateRequest.getHeadTeacher());
    return schoolClassDto;
  }


  public void deleteClass(Long classId)
  {

  }


  public Set<PupilDto> getPupilsForClass(Long classId)
  {
    return Collections.emptySet();
  }


  public PupilDto addPupilToClass(Long classId, PupilDto pupil)
  {
    return pupil;
  }


  public void removePupilFromClass(Long classId, Long pupilId)
  {

  }
}
