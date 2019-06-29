/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 11.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;


import lombok.Getter;
import lombok.Setter;


/**
 * @author M-VBE
 * @since 19.2
 */
@Getter
@Setter
public class PupilExternalDto
{
  private long id;

  private UserInfo userInfo;


  public PupilDto toInternal()
  {
    return new PupilDto(id, userInfo.getName());
  }
}
