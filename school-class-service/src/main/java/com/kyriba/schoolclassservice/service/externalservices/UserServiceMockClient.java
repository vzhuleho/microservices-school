/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 05.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices;


import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Profile({ "default", "test", "testcontainer"})
public class UserServiceMockClient implements UserServiceClient
{
  @Override
  public Optional<PupilDto> findById(PupilDto pupul)
  {
    return Optional.ofNullable(pupul);
  }


  @Override
  public Optional<HeadTeacherDto> findById(HeadTeacherDto teacher)
  {
    return Optional.ofNullable(teacher);
  }
}