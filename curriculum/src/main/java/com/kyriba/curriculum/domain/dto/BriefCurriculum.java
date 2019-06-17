/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * @author M-DBE
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BriefCurriculum
{
  @NonNull
  private long id;
  @NonNull
  private int grade;
}
