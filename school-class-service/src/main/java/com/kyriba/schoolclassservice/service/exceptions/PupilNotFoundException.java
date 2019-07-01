/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 26.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author M-VBE
 * @since 19.2
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PupilNotFoundException extends RuntimeException
{
  public PupilNotFoundException(Long classId)
  {
    super("Pupil with id=" + classId + "not found");
  }
}
