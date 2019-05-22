/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api.exception;


/**
 * @author M-DBE
 */
public class CourseAlreadyExistsException extends CurriculumServiceException
{
  public CourseAlreadyExistsException(long curriculumId, long subjectId)
  {
    super(String.format("Course for curriculum with id %d and subject with id %d already exists.",
        curriculumId, subjectId));
  }
}
