/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.dto.SubjectToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectToUpdateDTO;
import com.kyriba.curriculum.service.SubjectService;
import com.kyriba.curriculum.service.exception.SubjectAlreadyExistsException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.config.MockMvcConfig.mockMvcConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;


/**
 * @author M-DBE
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureRestDocs
@ActiveProfiles("test")
class SubjectControllerTest
{
  @Mock
  private SubjectService subjectService;
  @InjectMocks
  private SubjectController subjectController;

  private RestAssuredMockMvcConfig config;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    RestAssuredMockMvc.standaloneSetup(subjectController, new ValidationExceptionHandler(),
        new SubjectController.ControllerExceptionHandler(),
        MockMvcRestDocumentation.documentationConfiguration(restDocumentation));
    config = RestAssuredMockMvcConfig.config()
        .mockMvcConfig(mockMvcConfig().automaticallyApplySpringRestDocsMockMvcSupport());
  }


  @Nested
  @DisplayName("Create subject")
  class SubjectCreate
  {
    @Test
    void should_return_subject_when_subject_for_name_created_successfully()
    {
      SubjectToCreateDTO subjectToCreate = new SubjectToCreateDTO("chemistry");
      Mockito.when(subjectService.createSubject(eq(subjectToCreate)))
          .thenReturn(new SubjectDTO(1, subjectToCreate.getName()));

      SubjectDTO subject = given()
          .config(config)
          .body(subjectToCreate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-success"))
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", SubjectDTO.class);

      assertNotNull(subject);
      assertEquals(subjectToCreate.getName(), subject.getName());
    }


    @Test
    void should_return_CONFLICT_status_when_subject_for_name_already_exists()
    {
      SubjectToCreateDTO subjectToCreate = new SubjectToCreateDTO("chemistry");
      Mockito.when(subjectService.createSubject(eq(subjectToCreate)))
          .thenThrow(new SubjectAlreadyExistsException(subjectToCreate.getName()));

      given()
          .config(config)
          .body("{\n" +
              "  \"name\" : \"chemistry\"\n" +
              "}")
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-fail-already-exists"))
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @ParameterizedTest
    @ValueSource(classes = {ValidationException.class, ConstraintViolationException.class })
    void should_return_BAD_REQUEST_status_when_validation_exception_is_thrown(Class<? extends Throwable> exception)
    {
      SubjectToCreateDTO subjectToCreate = new SubjectToCreateDTO("subject");
      when(subjectService.createSubject(subjectToCreate)).thenThrow(exception);

      given()
          .config(config)
          .body(subjectToCreate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-fail-validation"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void should_return_BAD_REQUEST_status_when_no_body()
    {
      given()
          .config(config)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-fail-no-body"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void should_return_UNSUPPORTED_MEDIA_TYPE_status_when_content_type_is_not_json()
    {
      given()
          .config(config)
          .contentType(MediaType.APPLICATION_XML_VALUE)
          .body("{\n" +
              "  \"name\" : \"chemistry\"\n" +
              "}")
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-fail-content-type"))
          .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }


    @Test
    void should_return_NOT_ACCEPTABLE_status_when_content_type_is_not_json()
    {
      given()
          .config(config)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .accept(MediaType.APPLICATION_XML_VALUE)
          .body("{\n" +
              "  \"name\" : \"chemistry\"\n" +
              "}")
          .when()
          .post("/api/v1/subjects")
          .then()
          .apply(document("create-subject-fail-content-type"))
          .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }
  }


  @Nested
  @DisplayName("Update subject")
  class SubjectUpdate
  {
    @Test
    void should_return_nothing_when_update_was_successful()
    {
      long subjectId = 1;
      SubjectToUpdateDTO subjectToUpdate = new SubjectToUpdateDTO("chemistry");
      doNothing().when(subjectService).updateSubject(subjectId, subjectToUpdate);

      given()
          .config(config)
          .body(subjectToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/subjects/{id}", subjectId)
          .then()
          .apply(document("update-subject-success"))
          .statusCode(HttpStatus.NO_CONTENT.value());

      verify(subjectService).updateSubject(subjectId, subjectToUpdate);
    }


    @Test
    void should_return_NOT_FOUND_status_when_subject_for_id_not_found()
    {
      long subjectId = 1;
      SubjectToUpdateDTO subjectToUpdate = new SubjectToUpdateDTO("chemistry");
      doThrow(new SubjectNotFoundException(subjectId)).when(subjectService).updateSubject(subjectId, subjectToUpdate);

      ErrorMessage message = given()
          .config(config)
          .body(subjectToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/subjects/{id}", subjectId)
          .then()
          .apply(document("update-subject-fail-subject-id-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().jsonPath().getObject(".", ErrorMessage.class);

      assertNotNull(message);
      assertEquals("Subject with id 1 not found.", message.getMessage());
    }
  }


  @Nested
  @DisplayName("Get all subjects")
  class SubjectGetAll
  {
    @Test
    void should_return_all_subjects_when_get_all_subjects()
    {
      List<SubjectDTO> subjects = List.of(new SubjectDTO(1, "algebra"),
          new SubjectDTO(2, "geometry"));
      Mockito.when(subjectService.getAllSubjects()).thenReturn(subjects);

      List<SubjectDTO> result = given()
          .config(config)
          .when()
          .get("/api/v1/subjects")
          .then()
          .apply(document("get-all-subjects"))
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", SubjectDTO.class);

      assertNotNull(result);
      assertEquals(Set.of(subjects), Set.of(result));
    }
  }
}
