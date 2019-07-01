/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.repository.TeacherRepository;
import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@Component
@RequiredArgsConstructor
@Primary
public class TeacherSerivceCachingDecorator implements TeacherServiceClient
{
  @Qualifier("regular")
  private final TeacherServiceClient teacherServiceClient;
  private final TeacherRepository teacherRepository;


  @Override
  public Optional<HeadTeacherEntity> findById(HeadTeacherDto headTeacherDto)
  {
    return Optional.ofNullable(findAndCacheIfPresent(headTeacherDto));
  }


  @Nullable
  private HeadTeacherEntity findAndCacheIfPresent(final HeadTeacherDto headTeacherDto)
  {
    HeadTeacherEntity teacher = null;
    if (headTeacherDto != null) {
      final Long teacherId = headTeacherDto.getId();
      //is it exists in the local storage?
      final Optional<HeadTeacherEntity> teacherLocalStorage = teacherRepository.findById(teacherId);
      if (teacherLocalStorage.isPresent()) {
        teacher = teacherLocalStorage.get();
      }
      else {
        teacher = findExternally(headTeacherDto, teacher);
      }
    }
    return teacher;
  }


  private HeadTeacherEntity findExternally(HeadTeacherDto headTeacherDto, HeadTeacherEntity teacher)
  {
    final Optional<HeadTeacherEntity> externalTeacherEntity = teacherServiceClient.findById(headTeacherDto);
    if (externalTeacherEntity.isPresent()) {
      teacher = teacherRepository.save(externalTeacherEntity.get());
    }
    return teacher;
  }

}
