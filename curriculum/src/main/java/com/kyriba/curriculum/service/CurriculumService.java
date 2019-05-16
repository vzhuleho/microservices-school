/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.api.dto.BriefCurriculum;
import com.kyriba.curriculum.api.dto.Curriculum;

import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
public interface CurriculumService
{
  Optional<Curriculum> findCurriculumById(long id);


  Optional<Curriculum> findCurriculumByGrade(int grade);


  List<BriefCurriculum> getAllCurricula();
}
