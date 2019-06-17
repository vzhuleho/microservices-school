/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.service.CurriculumService;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.config.MockMvcConfig.mockMvcConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;


/**
 * @author M-DBE
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureRestDocs
class CurriculumControllerTest
{
  @Mock
  private CurriculumService curriculumService;
  @InjectMocks
  private CurriculumController curriculumController;

  private RestAssuredMockMvcConfig config;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    RestAssuredMockMvc.standaloneSetup(curriculumController, new ValidationExceptionHandler(),
        new CustomExceptionHandler(), MockMvcRestDocumentation.documentationConfiguration(restDocumentation));
    config = RestAssuredMockMvcConfig.config()
        .mockMvcConfig(mockMvcConfig().automaticallyApplySpringRestDocsMockMvcSupport());
  }


  @Nested
  @DisplayName("Get curriculum by id")
  class CurriculumGet
  {
    @Test
    void should_return_NOT_FOUND_status_when_curriculum_not_found_for_id()
    {
      long curriculumId = 10;
      Mockito.when(curriculumService.getCurriculumById(eq(curriculumId)))
          .thenThrow(new CurriculumNotFoundException(curriculumId));

      given()
          .config(config)
          .when()
          .get("/api/v1/curricula/{id}", curriculumId)
          .then()
          .apply(document("get-curriculum-by-id-fail-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void should_return_full_curriculum_when_curriculum_exists_for_id()
    {
      long curriculumId = 1;
      Curriculum curriculum = new Curriculum(curriculumId, 10, List.of());
      Mockito.when(curriculumService.getCurriculumById(curriculumId)).thenReturn(curriculum);

      Curriculum result = given()
          .config(config)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .get("/api/v1/curricula/{id}", 1)
          .then()
          .apply(document("get-curriculum-by-id-success"))
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getObject(".", Curriculum.class);

      assertEquals(curriculum, result);
    }
  }


  @Nested
  @DisplayName("Get curriculum by grade")
  class CurriculumGetByGrade
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_exists_for_grade()
    {
      Integer grade = 5;
      Curriculum curriculum = new Curriculum(2, grade, List.of());
      Mockito.when(curriculumService.findCurriculumByGrade(grade)).thenReturn(Optional.of(curriculum));

      List<BriefCurriculum> curricula = given()
          .config(config)
          .queryParam("grade", grade)
          .when()
          .get("/api/v1/curricula")
          .then()
          .apply(document("get-curriculum-by-grade-success"))
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", BriefCurriculum.class);

      assertNotNull(curricula);
      assertEquals(1, curricula.size());
      assertEquals(curriculum.getId(), curricula.get(0).getId());
      assertEquals(grade, curricula.get(0).getGrade());
    }


    @Test
    void should_return_NOT_IMPLEMENTED_status_when_curriculum_not_found_for_grade()
    {
      Integer grade = 1;
      Mockito.when(curriculumService.findCurriculumByGrade(grade)).thenReturn(Optional.empty());

      given()
          .config(config)
          .queryParam("grade", grade)
          .when()
          .get("/api/v1/curricula")
          .then()
          .apply(document("get-curriculum-by-grade-fail-not-found"))
          .statusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }


    @ParameterizedTest
    @ValueSource(classes = { ValidationException.class, ConstraintViolationException.class })
    void should_return_BAD_REQUEST_status_when_validation_exception_is_thrown(Class<? extends Throwable> exception)
    {
      Integer grade = 1;
      Mockito.when(curriculumService.findCurriculumByGrade(grade)).thenThrow(exception);

      given()
          .config(config)
          .queryParam("grade", grade)
          .when()
          .get("/api/v1/curricula")
          .then()
          .apply(document("get-curriculum-by-grade-fail-validation-exception"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }
  }


  @Nested
  @DisplayName("Get all curricula")
  class CurriculumGetAll
  {
    @Test
    void should_return_all_curricula_in_brief()
    {
      List<BriefCurriculum> allCurricula = List.of(new BriefCurriculum(1, 1), new BriefCurriculum(2, 2));
      Mockito.when(curriculumService.findAllCurricula()).thenReturn(allCurricula);

      List<BriefCurriculum> result = given()
          .config(config)
          .when()
          .get("/api/v1/curricula")
          .then()
          .apply(document("get-all-curricula"))
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", BriefCurriculum.class);

      assertEquals(Set.copyOf(allCurricula), Set.copyOf(result));
    }
  }


  @Nested
  @DisplayName("Create curriculum")
  class CurriculumCreate
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_for_grade_created_successfully()
    {
      CurriculumToCreate curriculumToCreate = new CurriculumToCreate(5);
      BriefCurriculum createdCurriculum = new BriefCurriculum(1, curriculumToCreate.getGrade());
      Mockito.when(curriculumService.createCurriculum(curriculumToCreate)).thenReturn(createdCurriculum);

      BriefCurriculum result = given()
          .config(config)
          .body(curriculumToCreate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula")
          .then()
          .apply(document("create-curriculum-success"))
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", BriefCurriculum.class);

      assertNotNull(result);
      assertEquals(createdCurriculum, result);
    }


    @Test
    void should_return_CONFLICT_status_when_curriculum_for_grade_already_exists()
    {
      CurriculumToCreate curriculumToCreate = new CurriculumToCreate(11);
      Mockito.when(curriculumService.createCurriculum(curriculumToCreate))
          .thenThrow(new CurriculumAlreadyExistsException(curriculumToCreate.getGrade()));

      given()
          .config(config)
          .body(curriculumToCreate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula")
          .then()
          .apply(document("create-curriculum-fail-already-exists"))
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @ParameterizedTest
    @ValueSource(classes = { ValidationException.class, ConstraintViolationException.class })
    void should_return_BAD_REQUEST_status_when_validation_exception_is_thrown(Class<? extends Throwable> exception)
    {
      CurriculumToCreate curriculumToCreate = new CurriculumToCreate(11);
      Mockito.when(curriculumService.createCurriculum(curriculumToCreate))
          .thenThrow(exception);

      given()
          .config(config)
          .body(curriculumToCreate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula")
          .then()
          .apply(document("create-curriculum-fail-validation-exception"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }
  }


  @Nested
  @DisplayName("Remove curriculum")
  class CurriculumRemove
  {
    @Test
    void should_return_nothing_when_curriculum_for_id_exists()
    {
      long curriculumId = 1;
      doNothing().when(curriculumService).removeCurriculum(curriculumId);

      given()
          .config(config)
          .when()
          .delete("/api/v1/curricula/{id}", curriculumId)
          .then()
          .apply(document("remove-curriculum-success"))
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_curriculum_for_id_not_found()
    {
      long curriculumId = 1;
      doThrow(new CurriculumNotFoundException(curriculumId)).when(curriculumService).removeCurriculum(curriculumId);

      given()
          .config(config)
          .when()
          .delete("/api/v1/curricula/{id}", curriculumId)
          .then()
          .apply(document("remove-curriculum-fail-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value());
    }
  }


  @Nested
  @DisplayName("Course add")
  class CourseAdd
  {
    @Test
    void should_return_NOT_FOUND_with_message_when_curriculum_for_id_not_found()
    {
      long curriculumId = 1;
      CourseToAdd courseToAdd = new CourseToAdd(2, 100);
      Mockito.when(curriculumService.addCourse(curriculumId, courseToAdd))
          .thenThrow(new CurriculumNotFoundException(curriculumId));

      String message = given()
          .config(config)
          .body(courseToAdd)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula/{id}/courses", curriculumId)
          .then()
          .apply(document("add-course-fail-curriculum-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 1 not found.", message);
    }


    @Test
    void should_return_NOT_FOUND_with_message_when_subject_for_id_not_found()
    {
      long curriculumId = 1;
      CourseToAdd courseToAdd = new CourseToAdd(2, 100);
      Mockito.when(curriculumService.addCourse(curriculumId, courseToAdd))
          .thenThrow(new SubjectNotFoundException(courseToAdd.getSubjectId()));

      String message = given()
          .config(config)
          .body(courseToAdd)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula/{id}/courses", curriculumId)
          .then()
          .apply(document("add-course-fail-subject-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Subject with id 2 not found.", message);
    }


    @Test
    void should_return_CONFLICT_with_message_when_course_for_subject_already_exists()
    {
      long curriculumId = 1;
      CourseToAdd courseToAdd = new CourseToAdd(2, 100);
      Mockito.when(curriculumService.addCourse(curriculumId, courseToAdd))
          .thenThrow(new CourseAlreadyExistsException(curriculumId, courseToAdd.getSubjectId()));

      String message = given()
          .config(config)
          .body(courseToAdd)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula/{id}/courses", curriculumId)
          .then()
          .apply(document("add-course-fail-already-exists"))
          .statusCode(HttpStatus.CONFLICT.value())
          .extract().response().asString();

      assertEquals("Course for curriculum with id 1 and subject with id 2 already exists.", message);
    }


    @Test
    void should_return_course_when_course_added_successfully()
    {
      long curriculumId = 1;
      CourseToAdd courseToAdd = new CourseToAdd(2, 100);
      Course course = new Course(10, new Subject(courseToAdd.getSubjectId(), "Algebra"), courseToAdd.getLessonCount());
      Mockito.when(curriculumService.addCourse(curriculumId, courseToAdd))
          .thenReturn(course);

      Course result = given()
          .config(config)
          .body(courseToAdd)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula/{id}/courses", curriculumId)
          .then()
          .apply(document("add-course-success"))
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", Course.class);

      assertNotNull(course);
      assertEquals(course, result);
    }


    @ParameterizedTest
    @ValueSource(classes = { ValidationException.class, ConstraintViolationException.class })
    void should_return_BAD_REQUEST_when_validation_exception_is_thrown(Class<? extends Throwable> exception)
    {
      long curriculumId = 1;
      CourseToAdd courseToAdd = new CourseToAdd(2, 100);
      Mockito.when(curriculumService.addCourse(curriculumId, courseToAdd)).thenThrow(exception);

      given()
          .config(config)
          .body(courseToAdd)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/api/v1/curricula/{id}/courses", curriculumId)
          .then()
          .apply(document("add-course-fail-validation-exception"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }
  }


  @Nested
  @DisplayName("Update course")
  class CourseUpdate
  {
    @Test
    void should_return_nothing_when_updated_successfully()
    {
      long curriculumId = 1;
      long courseId = 100;
      CourseToUpdate courseToUpdate = new CourseToUpdate(1000, 300);
      doNothing().when(curriculumService).updateCourse(curriculumId, courseId, courseToUpdate);

      given()
          .config(config)
          .body(courseToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("update-course-replaced-success"))
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_course_for_id_not_found()
    {
      long curriculumId = 1;
      long courseId = 100;
      CourseToUpdate courseToUpdate = new CourseToUpdate(1003, 300);
      doThrow(new CourseNotFoundException(curriculumId, courseId)).when(curriculumService)
          .updateCourse(curriculumId, courseId, courseToUpdate);

      String message = given()
          .config(config)
          .body(courseToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("update-course-updated-fail-course-id-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertNotNull(message);
      assertEquals("Course with id 100 for curriculum with id 1 not found.", message);
    }


    @Test
    void should_return_CONFLICT_status_when_other_course_for_subject_exists()
    {
      long curriculumId = 1;
      long courseId = 100;
      CourseToUpdate courseToUpdate = new CourseToUpdate(1003, 300);
      doThrow(new CourseAlreadyExistsException(curriculumId, courseId)).when(curriculumService)
          .updateCourse(curriculumId, courseId, courseToUpdate);

      given()
          .config(config)
          .body(courseToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("update-course-fail-other-for-subject-exists"))
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_curriculum_for_id_not_found()
    {
      long curriculumId = 1;
      long courseId = 100;
      CourseToUpdate courseToUpdate = new CourseToUpdate(1003, 300);
      doThrow(new CurriculumNotFoundException(curriculumId)).when(curriculumService)
          .updateCourse(curriculumId, courseId, courseToUpdate);

      String message = given()
          .config(config)
          .body(courseToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("update-course-fail-curriculum-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 1 not found.", message);
    }


    @ParameterizedTest
    @ValueSource(classes = { ValidationException.class, ConstraintViolationException.class })
    void should_return_BAD_REQUEST_status_when_validation_exception_is_thrown(Class<? extends Throwable> exception)
    {
      long curriculumId = 1;
      long courseId = 100;
      CourseToUpdate courseToUpdate = new CourseToUpdate(1003, 300);
      doThrow(exception).when(curriculumService).updateCourse(curriculumId, courseId, courseToUpdate);

      given()
          .config(config)
          .body(courseToUpdate)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("update-course-fail-curriculum-not-found"))
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }
  }


  @Nested
  @DisplayName("Remove course")
  class CourseRemove
  {
    @Test
    void should_return_nothing_when_removed_successfully()
    {
      long curriculumId = 1;
      long courseId = 100;
      doNothing().when(curriculumService).removeCourse(curriculumId, courseId);

      given()
          .config(config)
          .when()
          .delete("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("remove-course-success"))
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_curriculum_for_id_not_found()
    {
      long curriculumId = 1;
      long courseId = 100;
      doThrow(new CurriculumNotFoundException(curriculumId)).when(curriculumService)
          .removeCourse(curriculumId, courseId);

      String message = given()
          .config(config)
          .when()
          .delete("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("remove-course-curriculum-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 1 not found.", message);
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_course_for_id_not_found()
    {
      long curriculumId = 1;
      long courseId = 100;
      doThrow(new CourseNotFoundException(curriculumId, courseId)).when(curriculumService)
          .removeCourse(curriculumId, courseId);

      String message = given()
          .config(config)
          .when()
          .delete("/api/v1/curricula/{curriculumId}/courses/{courseId}", curriculumId, courseId)
          .then()
          .apply(document("remove-course-curriculum-not-found"))
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Course with id 100 for curriculum with id 1 not found.", message);
    }
  }
}
