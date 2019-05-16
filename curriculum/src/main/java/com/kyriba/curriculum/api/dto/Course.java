/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api.dto;

import lombok.Value;


/**
 * @author M-DBE
 */
@Value
public class Course
{
  private long id;
  private Subject subject;
  private int lessonCount;
}
