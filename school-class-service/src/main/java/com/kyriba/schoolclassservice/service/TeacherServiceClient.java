/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 14.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@Component
//Mock of the external microservice
public class TeacherServiceClient
{
  //todo: here we should pass id only but now it's a mock so takes full dto
  public Optional<HeadTeacherDto> findById(HeadTeacherDto teacher)
  {
    return Optional.ofNullable(teacher);
  }
}
