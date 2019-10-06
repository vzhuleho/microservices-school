/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 12.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import com.kyriba.schoolclassservice.service.exceptions.SchoolClassNotFoundException;
import com.kyriba.schoolclassservice.service.externalservices.UserDataService;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author M-VBE
 * @since 19.2
 */
@RunWith(MockitoJUnitRunner.class)
public class SchoolClassServiceTest
{
  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  
  @Mock
  private SchoolClassRepository classRepository;

  @Mock
  private UserDataService userDataService;

  @InjectMocks
  public SchoolClassService schoolClassService;
  
  
  private SchoolClassEntity class1;
  private SchoolClassEntity class2;


  @Before
  public void setUp() throws Exception
  {
    class1 = createNewClass(1L, 2, 2000, "A", 33L, "Иванова Мария Петровна");
    class2 = createNewClass(2L, 11, 2008, "A", 23L, "Михайлова Мария Петровна");
  }


  private SchoolClassEntity createNewClass(long id, int grade, int year, String letter, long teacherId,
                                           String teacherName)
  {
    SchoolClassEntity classEntity = new SchoolClassEntity();
    classEntity.setId(id);
    classEntity.setGrade(grade);
    classEntity.setYear(year);
    classEntity.setLetter(letter);

    HeadTeacherEntity headTeacherEntity2 = new HeadTeacherEntity();
    headTeacherEntity2.setId(teacherId);
    headTeacherEntity2.setFullname(teacherName);
    classEntity.setHeadTeacherEntity(headTeacherEntity2);
    return classEntity;
  }


  @Test
  public void testGetAll()
  {
    //given
    List<SchoolClassEntity> value = Arrays.asList(class1, class2);
    when(classRepository.findAll()).thenReturn(value);

    //when
    List<SchoolClassDto> all = schoolClassService.getAll();


    Assert.assertThat(all, Matchers.hasSize(2));
    Assertions.assertThat(all)
        .extracting(
            SchoolClassDto::getId, SchoolClassDto::getGrade, SchoolClassDto::getLetter, SchoolClassDto::getYear)
        .contains(
            Tuple.tuple(class1.getId(), class1.getGrade(), class1.getLetter(), class1.getYear()),
            Tuple.tuple(class2.getId(), class2.getGrade(), class2.getLetter(), class2.getYear()));
  }


  @Test
  public void testGetById()
  {
    //given
    when(classRepository.findById(1L)).thenReturn(Optional.of(class1));

    //when
    SchoolClassDto singleDto = schoolClassService.getById(1L);

    //then
    Assertions.assertThat(singleDto)
        .extracting(SchoolClassDto::getId, SchoolClassDto::getGrade, SchoolClassDto::getLetter, SchoolClassDto::getYear)
        .contains(class1.getId(), class1.getGrade(), class1.getLetter(), class1.getYear());
  }


  @Test
  public void testGetById_repositoryReturnsEmpty()
  {
    //given
    when(classRepository.findById(any())).thenReturn(Optional.empty());

    //when
    expectedException.expect(SchoolClassNotFoundException.class);
    schoolClassService.getById(1L);
  }



  @Test
  public void testGetByIds_repositoryReturnsList()
  {
    //given
    when(classRepository.findByIdIn(Arrays.asList(1L, 2L)))
        .thenReturn(new ArrayList<>(Arrays.asList(class1, class2)));

    //when
    List<SchoolClassDto> resultCollection = schoolClassService.getByIds(Arrays.asList(1L, 2L));
    
    //then
    Assertions.assertThat(resultCollection)
        .hasSize(2)
        .extracting(
            SchoolClassDto::getId, SchoolClassDto::getGrade, SchoolClassDto::getLetter, SchoolClassDto::getYear)
        .contains(
            Tuple.tuple(class1.getId(), class1.getGrade(), class1.getLetter(), class1.getYear()),
            Tuple.tuple(class2.getId(), class2.getGrade(), class2.getLetter(), class2.getYear()));
  }


}
