/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto;

import com.kyriba.curriculum.domain.dto.constraint.GradeConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author M-DBE
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CurriculumToCreateDTO
{
  @GradeConstraint
  private int grade;
}
