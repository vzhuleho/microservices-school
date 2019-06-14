/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 9.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.dto;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


/**
 * @author M-VBE
 * @since 19.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "School class data")
public class SchoolClassDto
{
  @ApiModelProperty(value = "School class id", example = "123")
  Long id;
  @ApiModelProperty(value = "School class grade", example = "10")
  @Range(min = 1, max = 12)
  @NonNull
  Integer grade;
  @ApiModelProperty(value = "School class letter", example = "A")
  @Size(min = 1, max = 2)
  @NonNull
  String letter;
  @ApiModelProperty(value = "School class year", example = "2018")
  @Positive
  int year;
  @ApiModelProperty(value = "Class head teacher")
  HeadTeacherDto headTeacher;

    public static SchoolClassDto of(SchoolClassEntity entity) {
        return SchoolClassDto.builder()
                .id(entity.getId())
                .grade(entity.getGrade())
                .letter(entity.getLetter())
                .year(entity.getYear())
                .headTeacher(HeadTeacherDto) \\ todo:!@
                .build();
    }

    public SchoolClassEntity toEntity() {
        return SchoolClassEntity.builder()
                .id(getId())
                .grade(getGrade())
                .letter(getLetter())
                .year(getYear())
                .headTeacher()
                .build();
    }
}
