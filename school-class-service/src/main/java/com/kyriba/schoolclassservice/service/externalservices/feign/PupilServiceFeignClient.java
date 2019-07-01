/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 30.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices.feign;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.externalservices.PupilServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@FeignClient("${pupil.service.id}")
@Qualifier("regular")
public interface PupilServiceFeignClient extends PupilServiceClient
{
  @GetMapping(value = "/pupils/{id}")
  Optional<PupilDto> findById(@PathVariable("id") Long id);


  @Override
  default Optional<PupilEntity> findById(PupilDto pupul)
  {
    return findById(pupul.getId()).map(new PupilEntity()::populateFrom);
  }

}
