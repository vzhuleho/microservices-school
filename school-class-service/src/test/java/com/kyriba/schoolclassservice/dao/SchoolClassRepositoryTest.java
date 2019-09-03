/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 12.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * @author M-VBE
 * @since 19.2
 */
@DBRider
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testcontainer")
@DataSet(cleanAfter = true)
public class SchoolClassRepositoryTest
{
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Autowired
  SchoolClassRepository schoolClassRepository;

  @Autowired
  TestEntityManager entityManager;
  private SchoolClassEntity class1;
  private SchoolClassEntity class2;
  private SchoolClassEntity class3;


  @Before
  @Transactional
  @DataSet(cleanAfter = true)
  public void setUp() throws Exception
  {
    SchoolClassEntity schoolClassEntity1 = schoolClass("A", 2018, 6);
    SchoolClassEntity schoolClassEntity2 = schoolClass("B", 2017, 7);
    SchoolClassEntity schoolClassEntity3 = schoolClass("C", 2016, 8);
    this.class1 = entityManager.persistAndFlush(schoolClassEntity1);
    this.class2 = entityManager.persistAndFlush(schoolClassEntity2);
    this.class3 = entityManager.persistAndFlush(schoolClassEntity3);
  }


  @NotNull
  private SchoolClassEntity schoolClass(String letter, int year, int grade)
  {
    SchoolClassEntity schoolClassEntity = new SchoolClassEntity();
    schoolClassEntity.setLetter(letter);
    schoolClassEntity.setYear(year);
    schoolClassEntity.setGrade(grade);
    return schoolClassEntity;
  }


  @Test
  public void testGetAll()
  {
    //given storage with some classes

    //when
    List<SchoolClassEntity> all = schoolClassRepository.findAll();

    //then
    Assertions.assertThat(all).hasSize(3)
        .extracting("id")
        .contains(this.class1.getId(),
            this.class2.getId(),
            this.class3.getId());
  }


  @Test
  public void testFindById()
  {
    //given storage with some classes

    //when
    Optional<SchoolClassEntity> result = schoolClassRepository.findById(class1.getId());

    //then
    Assertions.assertThat(result).isPresent()
        .get()
        .extracting("id")
        .contains(this.class1.getId());
  }


  @Test
  public void testFindById_negativeCase()
  {
    //given storage with some classes

    //when
    Optional<SchoolClassEntity> result = schoolClassRepository.findById(Long.MAX_VALUE);
    Assertions.assertThat(result).isEmpty();
  }


  @Test
  public void ifClassAlreadyExistsItMayBeFoundUsingSeveralMethods()
  {
    final Long id = class1.getId();

    assertThat(schoolClassRepository.findAll(), hasItem(samePropertyValuesAs(class1)));
    assertThat(schoolClassRepository.findById(id).orElse(null), samePropertyValuesAs(class1));
    assertThat(schoolClassRepository.getOne(id), samePropertyValuesAs(class1));
    assertThat(schoolClassRepository.existsById(id), is(true));
  }


  @Test
  public void create()
  {
    //given storage with some classes
    SchoolClassEntity schoolClassEntity = new SchoolClassEntity();
    schoolClassEntity.setGrade(1);
    schoolClassEntity.setYear(1);
    schoolClassEntity.setLetter("A");

    //when
    schoolClassRepository.save(schoolClassEntity);
    SchoolClassEntity savedResult =
        entityManager.find(SchoolClassEntity.class, schoolClassEntity.getId());

    //when
    Assertions.assertThat(savedResult)
        .isEqualToComparingFieldByField(schoolClassEntity)
        .extracting(SchoolClassEntity::getId).isNotNull();
  }


}

