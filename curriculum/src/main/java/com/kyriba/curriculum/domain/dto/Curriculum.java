/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto;

import lombok.Value;

import java.util.List;


/**
 * @author M-DBE
 */
@Value
public class Curriculum
{
  private final long id;
  private final int grade;
  private final List<Course> courses;


  public BriefCurriculum toBrief()
  {
    return new BriefCurriculum(getId(), getGrade());
  }
}
