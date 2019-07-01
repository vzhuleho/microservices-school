/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * @author M-DBE
 */
public interface CurriculumRepository extends CrudRepository<CurriculumEntity, Long>
{
  Optional<CurriculumEntity> findByGrade(int grade);
}
