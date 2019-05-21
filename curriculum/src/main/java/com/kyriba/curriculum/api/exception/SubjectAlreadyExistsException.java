/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api.exception;

/**
 * @author M-DBE
 */
public class SubjectAlreadyExistsException extends CurriculumServiceException
{
  public SubjectAlreadyExistsException(String subjectName)
  {
    super(String.format("Subject with name %s already exists.", subjectName));
  }


  public SubjectAlreadyExistsException(long subjectId)
  {
    super(String.format("Subject with id %d already exists.", subjectId));
  }
}
