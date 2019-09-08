/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
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
@ActiveProfiles({ "test", "test-api" })
@Sql(scripts = { "clean-curricula.sql", "init-curricula.sql" })
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


  @Test
  void should_return_brief_curriculum_when_curriculum_for_grade_created_successfully()
  {
    given(spec)
        .filter(document("create-curriculum-success"))
        .body("{\n" +
            "  \"grade\" : 5\n" +
            "}")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
        .post("/curricula")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("grade", is(equalTo(5)))
        .body("id", is(greaterThan(0)));
  }


  @Test
  void should_return_CONFLICT_status_when_curriculum_for_grade_already_exists()
  {
    given(spec)
        .filter(document("create-curriculum-fail-already-exists"))
        .body("{ \"grade\" : 11 }")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
        .post("/curricula")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }
}

