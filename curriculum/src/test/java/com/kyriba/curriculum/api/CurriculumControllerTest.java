/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.Subject;
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
class CurriculumControllerTest
{
  private RequestSpecification spec;
  private List<Curriculum> prevCurricula;
  private List<Subject> prevSubjects;


  @BeforeEach
  void before(RestDocumentationContextProvider restDocumentation)
  {
    this.spec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .build();
    prevCurricula = NonProdData.CURRICULA;
    NonProdData.CURRICULA = NonProdData.DEFAULT_CURRICULA.get();
    prevSubjects = NonProdData.SUBJECTS;
    NonProdData.SUBJECTS = NonProdData.DEFAULT_SUBJECTS.get();
  }


  @AfterEach
  void after()
  {
    NonProdData.CURRICULA = prevCurricula;
    NonProdData.SUBJECTS = prevSubjects;
  }


  @Nested
  @DisplayName("Get curriculum")
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


    @Test
    void should_return_full_curriculum_when_curriculum_exists_for_grade()
    {
      Curriculum curriculum = given(spec)
          .filter(document("get-curriculum-by-grade-success"))
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
    void should_return_NOT_FOUND_status_when_curriculum_not_found_for_grade()
    {
      given(spec)
          .filter(document("get-curriculum-by-grade-fail-not-found"))
          .pathParam("grade", 1)
          .when()
          .get("/curricula/grades/{grade}")
          .then()
          .statusCode(HttpStatus.NOT_IMPLEMENTED.value());
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
          .pathParam("grade", 5)
          .when()
          .post("/curricula/grades/{grade}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", BriefCurriculum.class);

      assertNotNull(curriculum);
      assertEquals(5, curriculum.getGrade());
    }


    @Test
    void should_return_CONFLICT_status_when_curriculum_for_grade_already_exists()
    {
      given(spec)
          .filter(document("create-curriculum-fail-already-exists"))
          .pathParam("grade", 11)
          .when()
          .post("/curricula/grades/{grade}")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
    }
  }


  @Nested
  @DisplayName("Remove curriculum")
  class CurriculumRemove
  {
    @Test
    void should_return_brief_curriculum_when_curriculum_for_id_exists()
    {
      BriefCurriculum curriculum = given(spec)
          .filter(document("remove-curriculum-success"))
          .pathParam("id", 1)
          .when()
          .delete("/curricula/{id}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", BriefCurriculum.class);

      assertNotNull(curriculum);
      assertEquals(11, curriculum.getGrade());
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
  @DisplayName("Create subject")
  class SubjectCreate
  {
    @Test
    void should_return_subject_when_subject_for_name_created_successfully()
    {
      String subjectName = "chemistry";
      Subject subject = given(spec)
          .filter(document("create-subject-success"))
          .pathParam("name", subjectName)
          .when()
          .post("/curricula/subjects/{name}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", Subject.class);

      assertNotNull(subject);
      assertEquals(subjectName, subject.getName());
    }


    @Test
    void should_return_CONFLICT_status_when_subject_for_name_already_exists()
    {
      given(spec)
          .filter(document("create-subject-fail-already-exists"))
          .pathParam("name", "algebra")
          .when()
          .post("/curricula/subjects/{name}")
          .then()
          .statusCode(HttpStatus.CONFLICT.value());
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
          .get("/curricula/subjects")
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
          .body(new CourseToAdd(new Subject(1003, "physics"), 200))
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
          .body(new CourseToAdd(new Subject(1100, "chemistry"), 200))
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
          .body(new CourseToAdd(new Subject(1000, "algebra"), 200))
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
      Subject subject = new Subject(1003, "physics");
      Course course = given(spec)
          .filter(document("add-course-success"))
          .pathParam("id", 1)
          .body(new CourseToAdd(subject, 200))
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .post("/curricula/{id}/courses")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", Course.class);

      assertNotNull(course);
      assertEquals(subject, course.getSubject());
      assertEquals(200, course.getLessonCount());
    }
  }


  @Nested
  @DisplayName("Update course")
  class CourseUpdate
  {
    @Test
    void should_return_updated_course_when_updated_successfully()
    {
      Course course = given(spec)
          .filter(document("update-course-success"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 100)
          .param("lessonCount", 300)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", Course.class);

      assertNotNull(course);
      assertEquals(new Subject(1000, "algebra"), course.getSubject());
      assertEquals(300, course.getLessonCount());
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_curriculum_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("update-course-curriculum-not-found"))
          .pathParam("curriculumId", 10)
          .pathParam("courseId", 100)
          .param("lessonCount", 300)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Curriculum with id 10 not found.", message);
    }


    @Test
    void should_return_NOT_FOUND_status_with_message_when_course_for_id_not_found()
    {
      String message = given(spec)
          .filter(document("update-course-curriculum-not-found"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 125)
          .param("lessonCount", 300)
          .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
          .when()
          .put("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .extract().response().asString();

      assertEquals("Course with id 125 for curriculum with id 1 not found.", message);
    }
  }


  @Nested
  @DisplayName("Remove course")
  class CourseRemove
  {
    @Test
    void should_return_removed_course_when_removed_successfully()
    {
      Course course = given(spec)
          .filter(document("remove-course-success"))
          .pathParam("curriculumId", 1)
          .pathParam("courseId", 100)
          .when()
          .delete("/curricula/{curriculumId}/courses/{courseId}")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath().getObject(".", Course.class);

      assertNotNull(course);
      assertEquals(new Subject(1000, "algebra"), course.getSubject());
      assertEquals(100, course.getLessonCount());
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
