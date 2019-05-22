/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto;

import lombok.Value;

import javax.validation.constraints.Positive;


/**
 * @author M-DBE
 */
@Value
public class CourseToAdd
{
  private final long subjectId;
  @Positive
  private final int lessonCount;
}
