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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
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
class CurriculumControllerTest
{
  private RequestSpecification spec;
  private List<Curriculum> prevCurricula;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    this.spec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .setBasePath("/api/v1")
        .build();
    prevCurricula = CurriculumController.CURRICULA;
    CurriculumController.CURRICULA = CurriculumController.DEFAULT_CURRICULA.get();
  }


  @AfterEach
  void after()
  {
    CurriculumController.CURRICULA = prevCurricula;
  }


  @Nested
  @DisplayName("Get curriculum by id")
  class CurriculumGet
  {
    @Test
    void should_return_NOT_FOUND_status_when_curriculum_not_found_for_id()
    {
      given(spec)
          .filter(document("get-curriculum-by-id-fail-not-found"))
          .pathParam("id", 10)
          .when()
          .get("/curricula/{id}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void should_return_full_curriculum_when_curriculum_exists_for_id()
    {
      Curriculum curriculum = given(spec)
          .filter(document("get-curriculum-by-id-success"))
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
  }


  @Nested()
  @DisplayName("Get curriculum by grade")
  class CurriculumGetByGrade
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_exists_for_grade()
    {
      List<BriefCurriculum> curricula = given(spec)
          .filter(document("get-curriculum-by-grade-success"))
          .queryParam("grade", 10)
          .when()
          .get("/curricula")
          .then()
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", BriefCurriculum.class);

      assertNotNull(curricula);
      assertEquals(1, curricula.size());
      assertEquals(2, curricula.get(0).getId());
      assertEquals(10, curricula.get(0).getGrade());
    }


    @Test
    void should_return_NOT_FOUND_status_when_curriculum_not_found_for_grade()
    {
      given(spec)
          .filter(document("get-curriculum-by-grade-fail-not-found"))
          .queryParam("grade", 1)
          .when()
          .get("/curricula")
          .then()
          .statusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }


    @ParameterizedTest
    @ValueSource(ints = { -100, 0 })
    void should_return_BAD_REQUEST_status_when_grade_is_less_than_1(int grade)
    {
      String message = given(spec)
          .filter(document("get-curriculum-by-grade-fail-grade-is-less-than-1"))
          .queryParam("grade", grade)
          .when()
          .get("/curricula")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be greater than or equal to 1"));
    }


    @Test
    void should_return_BAD_REQUEST_status_when_grade_is_greater_than_11()
    {
      String message = given(spec)
          .filter(document("get-curriculum-by-grade-fail-grade-is-greater-than-11"))
          .queryParam("grade", 100)
          .when()
          .get("/curricula")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be less than or equal to 11"));
    }
  }


  @Nested
  @DisplayName("Get all curricula")
  class CurriculumGetAll
  {
    @Test
    void should_return_all_curricula_in_brief()
    {
      List<BriefCurriculum> curricula = given(spec)
          .filter(document("get-all-curricula"))
          .when()
          .get("/curricula")
          .then()
          .statusCode(HttpStatus.OK.value())
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .extract().jsonPath().getList(".", BriefCurriculum.class);

      assertNotNull(curricula);
      assertEquals(new HashSet<>(Arrays.asList(10, 11)), new HashSet<>(curricula.stream()
          .map(BriefCurriculum::getGrade)
          .collect(Collectors.toSet())));
    }
  }


  @Nested
  @DisplayName("Create curriculum")
  class CurriculumCreate
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_for_grade_created_successfully()
    {
      BriefCurriculum curriculum = given(spec)
          .filter(document("create-curriculum-success"))
          .body(new CurriculumToCreate(5))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", BriefCurriculum.class);

      assertNotNull(curriculum);
      assertEquals(5, curriculum.getGrade());
    }


    @Test
    void should_return_CONFLICT_status_when_curriculum_for_grade_already_exists()
    {
      given(spec)
          .filter(document("create-curriculum-fail-already-exists"))
          .body(new CurriculumToCreate(11))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @ParameterizedTest
    @ValueSource(ints = { -100, 0 })
    void should_return_BAD_REQUEST_status_when_grade_is_less_than_1(int grade)
    {
      String message = given(spec)
          .filter(document("create-curriculum-fail-grade-is-less-than-1"))
          .body(new CurriculumToCreate(grade))
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
          .body(new CurriculumToCreate(100))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be less than or equal to 11"));
    }
  }


  @Nested
  @DisplayName("Remove curriculum")
  class CurriculumRemove
  {
    @Test
    void should_return_nothing_when_curriculum_for_id_exists()
    {
      given(spec)
          .filter(document("remove-curriculum-success"))
          .pathParam("id", 1)
          .when()
          .delete("/curricula/{id}")
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_curriculum_for_id_not_found()
    {
      given(spec)
          .filter(document("remove-curriculum-fail-not-found"))
          .pathParam("id", 10)
          .when()
          .delete("/curricula/{id}")
          .then()
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
      String message = given(spec)
          .filter(document("add-course-fail-curriculum-not-found"))
          .pathParam("id", 11)
          .body(new CourseToAdd(1003, 200))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 11 not found.", message);
    }


    @Test
    void should_return_NOT_FOUND_with_message_when_subject_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("add-course-fail-subject-not-found"))
          .pathParam("id", 1)
          .body(new CourseToAdd(1100, 200))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Subject with id 1100 not found.", message);
    }


    @Test
    void should_return_CONFLICT_with_message_when_course_for_subject_already_exists()
    {
      String message = given(spec)
          .filter(document("add-course-fail-already-exists"))
          .pathParam("id", 1)
          .body(new CourseToAdd(1000, 200))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .extract().response().asString();

      assertEquals("Course for curriculum with id 1 and subject with id 1000 already exists.", message);
    }


    @Test
    void should_return_course_when_course_added_successfully()
    {
      Course course = given(spec)
          .filter(document("add-course-success"))
          .pathParam("id", 1)
          .body(new CourseToAdd(1003, 200))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract().jsonPath().getObject(".", Course.class);

      assertNotNull(course);
      assertEquals(1003, course.getSubject().getId());
      assertEquals(200, course.getLessonCount());
    }


    @ParameterizedTest
    @ValueSource(ints = { -100, 0 })
    void should_return_BAD_REQUEST_when_lessonCount_is_invalid(int lessonCount)
    {
      String message = given(spec)
          .filter(document("add-course-fail-lesson-count-is-invalid"))
          .pathParam("id", 11)
          .body(new CourseToAdd(1003, lessonCount))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be greater than 0"));
    }
  }


  @Nested
  @DisplayName("Update course")
  class CourseUpdate
  {
    @Test
    void should_return_nothing_when_updated_successfully()
    {
      given(spec)
          .filter(document("update-course-replaced-success"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 100)
          .body(new CourseToUpdate(1000, 300))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_course_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("update-course-updated-fail-course-id-not-found"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 110)
          .body(new CourseToUpdate(1003, 300))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertNotNull(message);
      assertEquals("Course with id 110 for curriculum with id 1 not found.", message);
    }


    @Test
    void should_return_CONFLICT_status_when_other_course_for_subject_exists()
    {
      given(spec)
          .filter(document("update-course-fail-other-for-subject-exists"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 100)
          .body(new CourseToUpdate(1001, 300))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_when_curriculum_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("update-course-fail-curriculum-not-found"))
          .pathParam("curriculumId", 10)
          .pathParam("courseId", 100)
          .body(new CourseToUpdate(1000, 300))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 10 not found.", message);
    }


    @ParameterizedTest
    @ValueSource(ints = { -100, 0 })
    void should_return_BAD_REQUEST_status_when_lessonCount_is_invalid(int lessonCount)
    {
      String message = given(spec)
          .filter(document("update-course-fail-lesson-count-is-invalid"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 125)
          .body(new CourseToUpdate(1000, lessonCount))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().response().asString();

      assertThat(message, containsString("must be greater than 0"));
    }
  }


  @Nested
  @DisplayName("Remove course")
  class CourseRemove
  {
    @Test
    void should_return_nothing_when_removed_successfully()
    {
      given(spec)
          .filter(document("remove-course-success"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 100)
          .when()
          .delete("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_curriculum_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("remove-course-curriculum-not-found"))
          .pathParam("curriculumId", 10)
          .pathParam("courseId", 100)
          .when()
          .delete("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 10 not found.", message);
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_course_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("remove-course-curriculum-not-found"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 125)
          .when()
          .delete("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Course with id 125 for curriculum with id 1 not found.", message);
    }
  }
}
