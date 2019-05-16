/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service.exception;


/**
 * @author M-DBE
 */
public class CourseNotFoundException extends CurriculumServiceException
{
  public CourseNotFoundException(long curriculumId, long courseId)
  {
    super(String.format("Course with id %d for curriculum with id %d not found.", courseId, curriculumId));
  }
}
