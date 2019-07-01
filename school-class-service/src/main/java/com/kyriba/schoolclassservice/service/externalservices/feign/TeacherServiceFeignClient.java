/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 30.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices.feign;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.externalservices.TeacherServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@FeignClient("${teacher.service.id}")
@Qualifier("regular")
public interface TeacherServiceFeignClient extends TeacherServiceClient
{
  @GetMapping(value = "/teachers/{id}")
  Optional<HeadTeacherDto> findById(@PathVariable("id") Long id);


  @Override
  default Optional<HeadTeacherEntity> findById(HeadTeacherDto pupul)
  {
    return findById(pupul.getId()).map(HeadTeacherEntity::fromDto);
  }

}
