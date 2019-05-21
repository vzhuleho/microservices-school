/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api.exception;

/**
 * @author M-DBE
 */
public class CurriculumAlreadyExistsException extends CurriculumServiceException
{
  public CurriculumAlreadyExistsException(int grade)
  {
    super(String.format("Curriculum for grade %d already exists.", grade));
  }
}
