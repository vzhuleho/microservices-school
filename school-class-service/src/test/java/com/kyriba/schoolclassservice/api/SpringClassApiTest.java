/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 7.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-VBE
 * @since 19.2
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
public class SpringClassApiTest
{

  @LocalServerPort
  private int port;

  @Value("${api.version.path}")
  private String apiPrefix;

  @Autowired
  private SchoolClassRepository schoolClassRepository;


  @Rule
  public final JUnitRestDocumentation restDocumentationRule = new JUnitRestDocumentation();

  private RequestSpecification requestSpecification;


  @Before
  public void setUp() throws Exception
  {
    RestAssured.port = port;
    requestSpecification = new RequestSpecBuilder()
        .setBasePath(apiPrefix)
        .addFilter(documentationConfiguration(restDocumentationRule)).build();
  }


  @Test
  public void getAllSchoolClasses()
  {
    given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-get-all"))
        .when()
        .get("/classes")
        .then()
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .statusCode(HttpStatus.OK.value());
  }


  @Test
  public void getSingleById()
  {
    //given
    SchoolClassEntity existingClass = schoolClassRepository.save(SchoolClassEntity.builder()
        .grade(1)
        .letter("A")
        .year(2010)
        .build());

    //when
    final SchoolClassDto schoolClass = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-get"))
        .when()
        .get("/classes/{id}", existingClass.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassDto.class);

    //then
    Assert.assertEquals(existingClass.getId(), schoolClass.getId());
  }


  @Test
  public void schoolClassCanBeCreated()
  {
    final SchoolClassController.ClassCreated schoolClass = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-create"))
        .body("{\n" +
            "  \"grade\": \"11\",\n" +
            "  \"letter\": \"A\",\n" +
            "  \"year\": \"2018\", \n" +
            "  \"headTeacher\": {\n" +
            "    \"id\": \"123\",\n" +
            "    \"name\": \"Иван Петрович\"\n" +
            "  }\n" +
            "}")
        .when()
        .post("/classes")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassController.ClassCreated.class);

    //then
    Assert.assertThat(schoolClass.getId(), Matchers.notNullValue());
  }


  @Test
  public void schoolClassCanBeUpdated()
  {
    //given
    SchoolClassEntity existingClass = schoolClassRepository.save(SchoolClassEntity.builder()
        .grade(1)
        .letter("A")
        .year(2010)
        .build());

    //when
    final SchoolClassDto schoolClass = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-update"))
        .body("{\n" +
            "  \"grade\": \"11\",\n" +
            "  \"letter\": \"A\",\n" +
            "  \"year\": \"2018\", \n" +
            "  \"headTeacher\": {\n" +
            "    \"id\": \"123\",\n" +
            "    \"name\": \"Иван Петрович\"\n" +
            "  }\n" +
            "}")
        .when()
        .put("/classes/{classId}", existingClass.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassDto.class);

    //then
    Assert.assertThat(schoolClass.getId(), Matchers.is(existingClass.getId()));
    Assert.assertThat(schoolClass.getGrade(), Matchers.is(11));
    Assert.assertThat(schoolClass.getLetter(), Matchers.is("A"));
    Assert.assertThat(schoolClass.getHeadTeacher().getId(), Matchers.is(123L));
    Assert.assertThat(schoolClass.getHeadTeacher().getName(), Matchers.is("Иван Петрович"));
  }

}
