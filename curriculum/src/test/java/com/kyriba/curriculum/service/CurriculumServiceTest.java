/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.entity.CurriculumRepository;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

/**
 * @author M-DBE
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig({ CurriculumServiceImpl.class, CurriculumServiceExceptionHandlingAspect.class })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ActiveProfiles("test")
class CurriculumServiceTest
{
  @MockBean
  private CurriculumRepository curriculumRepository;
  @MockBean
  private SubjectRepository subjectRepository;
  @Autowired
  private CurriculumService curriculumService;


  @Test
  void should_throw_not_found_exception_given_no_curriculum_for_id()
  {
    //given
    long curriculumId = 1;
    doThrow(EmptyResultDataAccessException.class).when(curriculumRepository).deleteById(curriculumId);

    //when/then
    assertThrows(CurriculumNotFoundException.class, () -> curriculumService.removeCurriculum(curriculumId));
  }
}
