/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum;

import com.kyriba.curriculum.api.dto.BriefCurriculum;
import com.kyriba.curriculum.api.dto.Curriculum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-DBE
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CurriculumApiTest
{
  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
  private RequestSpecification spec;


  @Before
  public void setUp()
  {
    this.spec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .build();
  }


  @Test
  public void curriculum_by_id_not_found()
  {
    given(spec)
        .filter(document("curriculum-by-id-not-found"))
        .pathParam("id", 10)
        .when()
        .get("/curricula/{id}")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }


  @Test
  public void curriculum_by_id_found()
  {
    Curriculum curriculum = given(spec)
        .filter(document("curriculum-by-id-found"))
        .pathParam("id", 1)
        .when()
        .get("/curricula/{id}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract().jsonPath().getObject(".", Curriculum.class);

    assertNotNull(curriculum);
    assertEquals(1, curriculum.getId());
    assertEquals(11, curriculum.getGrade());
  }


  @Test
  public void curriculum_by_grade_found()
  {
    Curriculum curriculum = given(spec)
        .filter(document("curriculum-by-grade-found"))
        .pathParam("grade", 10)
        .when()
        .get("/curricula/grades/{grade}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract().jsonPath().getObject(".", Curriculum.class);

    assertNotNull(curriculum);
    assertEquals(2, curriculum.getId());
    assertEquals(10, curriculum.getGrade());
  }


  @Test
  public void curriculum_by_grade_not_found()
  {
    given(spec)
        .filter(document("curriculum-by-grade-not-found"))
        .pathParam("grade", 1)
        .when()
        .get("/curricula/grades/{grade}")
        .then()
        .statusCode(HttpStatus.NOT_IMPLEMENTED.value());
  }


  @Test
  public void find_all_curricula()
  {
    List<BriefCurriculum> curricula = given(spec)
        .filter(document("find-all-curricula"))
        .when()
        .get("/curricula")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract().jsonPath().getList(".", BriefCurriculum.class);

    assertNotNull(curricula);
    assertEquals(2, curricula.size());
    assertEquals(new HashSet<>(Arrays.asList(10, 11)), new HashSet<>(curricula.stream()
        .map(BriefCurriculum::getGrade)
        .collect(Collectors.toSet())));
  }


}
