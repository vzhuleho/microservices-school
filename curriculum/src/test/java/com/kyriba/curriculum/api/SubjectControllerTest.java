/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.dto.SubjectToCreate;
import com.kyriba.curriculum.domain.dto.SubjectToUpdate;
import com.kyriba.curriculum.service.NonProdData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
class SubjectControllerTest
{
  private RequestSpecification spec;
  private List<Subject> prevSubjects;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    this.spec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .setBasePath("/api/v1")
        .build();
    prevSubjects = NonProdData.SUBJECTS;
    NonProdData.SUBJECTS = NonProdData.DEFAULT_SUBJECTS.get();
  }


  @AfterEach
  void after()
  {
    NonProdData.SUBJECTS = prevSubjects;
  }


  @Nested
  @DisplayName("Create subject")
  class SubjectCreate
  {
    @Test
    void should_return_subject_when_subject_for_name_created_successfully()
    {
      String subjectName = "chemistry";
      Subject subject = given(spec)
          .filter(document("create-subject-success"))
          .body(new SubjectToCreate(subjectName))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/subjects")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", Subject.class);

      assertNotNull(subject);
      assertEquals(subjectName, subject.getName());
    }


    @Test
    void should_return_CONFLICT_status_when_subject_for_name_already_exists()
    {
      given(spec)
          .filter(document("create-subject-fail-already-exists"))
          .body(new SubjectToCreate("algebra"))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/subjects")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void should_return_BAD_REQUEST_status_when_subject_name_is_invalid()
    {
      given(spec)
          .filter(document("create-subject-fail-name-is-invalid"))
          .body(new SubjectToCreate("1algebra"))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/subjects")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }
  }


  @Nested
  @DisplayName("Update subject")
  class SubjectUpdate
  {
    @Test
    void should_return_updated_subject_when_update_was_successful()
    {
      String subjectName = "chemistry";
      Subject subject = given(spec)
          .filter(document("update-subject-success"))
          .pathParam("id", 1000)
          .body(new SubjectToUpdate(subjectName))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/subjects/{id}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", Subject.class);

      assertNotNull(subject);
      assertEquals(subjectName, subject.getName());
    }


    @Test
    void should_return_newly_created_subject_when_subject_for_id_not_found()
    {
      String subjectName = "chemistry";
      Subject subject = given(spec)
          .filter(document("update-subject-success"))
          .pathParam("id", 999)
          .body(new SubjectToUpdate(subjectName))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/subjects/{id}")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", Subject.class);

      assertNotNull(subject);
      assertEquals(subjectName, subject.getName());
    }
  }


  @Nested
  @DisplayName("Get all subjects")
  class SubjectGetAll
  {
    @Test
    void should_return_all_subjects_when_get_all_subjects()
    {
      List<Subject> subjects = given(spec)
          .filter(document("get-all-subjects"))
          .when()
          .get("/subjects")
          .then()
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", Subject.class);

      assertNotNull(subjects);
      assertEquals(4, subjects.size());
      assertEquals(new HashSet<>(Arrays.asList("algebra", "geometry", "english", "physics")),
          new HashSet<>(subjects.stream()
              .map(Subject::getName)
              .collect(Collectors.toSet())));

    }
  }
}
