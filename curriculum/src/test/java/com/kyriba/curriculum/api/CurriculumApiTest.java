/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculumDTO;
import com.kyriba.curriculum.domain.dto.CurriculumToCreateDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-DBE
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Testcontainers
@Disabled
class CurriculumApiTest
{
  private RequestSpecification spec;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    this.spec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .setBasePath("/api/v1")
        .build();
  }


  @Nested
  @DisplayName("Create curriculum")
  class CurriculumCreate
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_for_grade_created_successfully()
    {
      BriefCurriculumDTO curriculum = given(spec)
          .filter(document("create-curriculum-success"))
          .body("{\n" +
              "  \"grade\" : 5\n" +
              "}")
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().get("grade");

      assertNotNull(curriculum);
      assertEquals(5, curriculum.getGrade());
    }


    /*@Test
    void should_return_CONFLICT_status_when_curriculum_for_grade_already_exists()
    {
      given(spec)
          .filter(document("create-curriculum-fail-already-exists"))
          .body(new CurriculumToCreateDTO(11))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
    }*/


    /*@ParameterizedTest
    @ValueSource(ints = { -100, 0 })
    void should_return_BAD_REQUEST_status_when_grade_is_less_than_1(int grade)
    {
      String message = given(spec)
          .filter(document("create-curriculum-fail-grade-is-less-than-1"))
          .body(new CurriculumToCreateDTO(grade))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be greater than or equal to 1"));
    }


    @Test
    void should_return_BAD_REQUEST_status_when_grade_is_greater_than_11()
    {
      String message = given(spec)
          .filter(document("create-curriculum-fail-grade-is-greater-than-11"))
          .body(new CurriculumToCreateDTO(100))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be less than or equal to 11"));
    }*/
  }
}

