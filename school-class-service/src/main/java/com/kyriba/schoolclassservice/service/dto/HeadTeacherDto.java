/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 16.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * @author M-VBE
 * @since 19.2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Head teacher data")
@Accessors
public class HeadTeacherDto
{
  @ApiModelProperty(value = "Teacher id", example = "123", required = true)
  @NotNull
  Long id;
  @ApiModelProperty(value = "Teacher name", example = "Indiana Jones", required = true)
  @NotNull
  String name;

  //todo: Replace with some mapping framework
  public static HeadTeacherDto of(HeadTeacherEntity entity){
    HeadTeacherDto.builder()
        .id(entity.getId())
        .name(entity.getFullname())
        .build();
  }
}
