/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices.mocks;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.externalservices.TeacherServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@ConditionalOnProperty(value = "eureka.enabled", havingValue = "false")
@Component
@Qualifier("cacheable")
public class FakeTeacherServiceClient implements TeacherServiceClient
{
  @Override
  public Optional<HeadTeacherEntity> findById(HeadTeacherDto teacher)
  {
    return Optional.of(HeadTeacherEntity.fromDto(teacher));
  }

}
