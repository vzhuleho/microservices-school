/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices.mocks;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.externalservices.PupilServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@ConditionalOnProperty(value = "eureka.enabled", havingValue = "false")
@Qualifier("regular")
@Component
public class FakePupilServiceClient implements PupilServiceClient
{
  @Override
  public Optional<PupilEntity> findById(PupilDto pupul)
  {
    return Optional.of(new PupilEntity().populateFrom(pupul));
  }
}
