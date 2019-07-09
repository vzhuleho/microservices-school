/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 05.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices.dto;


import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadTeacherExternalDto
{
    private long id;

    private UserInfo userInfo;


    public HeadTeacherDto toInternal()
    {
        return new HeadTeacherDto(id, userInfo.getName());
    }

}
