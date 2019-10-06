/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 12.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.controllers.integration;

import com.kyriba.schoolclassservice.api.SchoolClassController;
import com.kyriba.schoolclassservice.service.PupilService;
import com.kyriba.schoolclassservice.service.SchoolClassService;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import com.kyriba.schoolclassservice.service.exceptions.SchoolClassNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author M-VBE
 * @since 19.2
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SchoolClassController.class)
@ActiveProfiles("test")
public class SchoolClassControllerIntegrationTest
{
  @Value("${api.version.path}")
  public String apiVersion;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PupilService pupilService;

  @MockBean
  private SchoolClassService schoolClassService;


  @Test
  public void testGet() throws Exception
  {
    //given
    SchoolClassDto schoolClassDto = new SchoolClassDto();
    schoolClassDto.setId(1L);
    schoolClassDto.setYear(2018);
    schoolClassDto.setGrade(6);
    when(schoolClassService.getById(1L)).thenReturn(schoolClassDto);

    //when & then
    mockMvc.perform(get(apiVersion + "/classes/1")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
        .andExpect(jsonPath("$.year", Matchers.equalTo(2018)))
        .andExpect(jsonPath("$.grade", Matchers.equalTo(6)));
  }


  @Test
  public void testPost_validationException() throws Exception
  {
    //given
    when(schoolClassService.create(any())).thenThrow(ValidationException.class);

    //when & then
    mockMvc.perform(post(apiVersion + "/classes/")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n" +
            "  \"id\": 1,\n" +
            "  \"grade\": 11\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }



  @Test
  public void testPost() throws Exception
  {
    //given
    when(schoolClassService.create(any())).thenThrow(ValidationException.class);

    //when & then
    mockMvc.perform(post(apiVersion + "/classes/")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n" +
            "  \"id\": 1,\n" +
            "  \"grade\": 11,\n" +
            "  \"year\": 2018\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }



  @Test
  public void testPost_customException() throws Exception
  {
    //given
    when(schoolClassService.getById(any())).thenThrow(SchoolClassNotFoundException.class);

    //when & then
    mockMvc.perform(get(apiVersion + "/classes/623")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
  }


}
