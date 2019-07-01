/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.repository.PupilRepository;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@Component
@RequiredArgsConstructor
@Primary
public class PupilSerivceCachingDecorator implements PupilServiceClient
{
  @Qualifier("regular")
  private final PupilServiceClient pupilServiceClient;
  private final PupilRepository pupilRepository;


  @Override
  public Optional<PupilEntity> findById(PupilDto pupul)
  {
    return Optional.ofNullable(readPupil(pupul));
  }


  private PupilEntity readPupil(@Valid final PupilDto pupilDto)
  {
    final Long id = pupilDto.getId();
    final Optional<PupilEntity> localStoragePupil = pupilRepository.findById(id);
    return localStoragePupil
        .orElseGet(() -> pupilServiceClient.findById(pupilDto).orElse(null));
  }
}
