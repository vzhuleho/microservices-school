/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.api.dto.BriefCurriculum;
import com.kyriba.curriculum.api.dto.Curriculum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
@Service
@Profile({"default", "prod"})
public class CurriculumServiceImpl implements CurriculumService
{
  @Override
  public Optional<Curriculum> findCurriculumById(long id)
  {
    return Optional.empty();
  }


  @Override
  public Optional<Curriculum> findCurriculumByGrade(int grade)
  {
    return Optional.empty();
  }


  @Override
  public List<BriefCurriculum> getAllCurricula()
  {
    return Collections.emptyList();
  }
}
