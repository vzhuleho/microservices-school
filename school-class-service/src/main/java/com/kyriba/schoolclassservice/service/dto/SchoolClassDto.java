/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 9.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * @author M-VBE
 * @since 19.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@ApiModel(value = "School class data")
public class SchoolClassDto
{
  @ApiModelProperty(value = "School class id", example = "123")
  Long id;
  @ApiModelProperty(value = "School class grade", example = "10")
  String grade;
  @ApiModelProperty(value = "School class letter", example = "A")
  String letter;
  @ApiModelProperty(value = "School class year", example = "2018")
  int year;
  @ApiModelProperty(value = "Class head teacher")
  HeadTeacherDto headTeacher;
}
