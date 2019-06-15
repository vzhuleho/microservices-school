/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 5.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;


/**
 * @author M-VBE
 * @since 19.2
 */
@Data
public class ClassUpdateRequest
{
  @ApiModelProperty(value = "Class head teacher")
  HeadTeacherDto headTeacher;
  @ApiModelProperty(value = "School class grade", example = "10")
  @Range(min = 1, max = 12)
  Integer grade;
  @ApiModelProperty(value = "School class letter", example = "A")
  @Size(min = 1, max = 1)
  String letter;


  public SchoolClassEntity toEntity(SchoolClassEntity entity) {
    entity.setGrade(grade);
    entity.setLetter(letter);
    return entity;
  }
}
