/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;


/**
 * @author M-DBE
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CurriculumDTO
{
  private long id;
  private int grade;
  @NonNull
  private List<CourseDTO> courses;


  public BriefCurriculumDTO toBrief()
  {
    return new BriefCurriculumDTO(getId(), getGrade());
  }
}
