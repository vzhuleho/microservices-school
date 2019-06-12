/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 11.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author M-VBE
 * @since 19.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Pupil data")
public class PupilDto
{
  @ApiModelProperty(value = "Pupil id", example = "123", required = true)
  @NotNull
  Long id;
  @ApiModelProperty(value = "Pupil full name", example = "Indiana Jones", required = true)
  @Size(min = 3, max = 50)
  @NotNull
  String name;

  public static PupilDto of(PupilEntity entity) {
    return PupilDto.builder()
        .id(entity.getId())
        .name(entity.getFullname())
        .build();
  }

  public PupilEntity toEntity() {
    return PupilEntity.builder()
        .id(getId())
        .fullname(this.getName())
        .build();
  }
}
