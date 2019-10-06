/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 7.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
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
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-VBE
 * @since 19.2
 */
//@DBRider //for some reason DBRider not closes connections if cache connection is false
@ActiveProfiles("testcontainer")
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
  private WebApplicationContext context;

  //DbRider's cache connection works very strange because it caches connection between tests in static var so 
  //different tests may reuse same connection even if new database was specified in context
  @Rule
  public DBUnitRule dbUnitRule = DBUnitRule.instance("system",
      () -> context.getBean(DataSource.class).getConnection());


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
  @DataSet(value = "datasets/classes-and-pupils.yml", cleanAfter = true, executorId = "system")
  public void getAllSchoolClasses()
  {
    List<SchoolClassDto> result = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-get-all"))
        .when()
        .get("/classes")
        .then()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .statusCode(HttpStatus.OK.value())
        .extract()
        .jsonPath()
        .getList("", SchoolClassDto.class);
    
    //then
    assertThat(result).hasSize(2)
        .extracting(SchoolClassDto::getGrade, SchoolClassDto::getLetter, SchoolClassDto::getYear)
        .contains(
            tuple(1, "A", 2010),
            tuple(2, "Б", 2009));
  }


  @Test
  @DataSet(value = "datasets/classes-and-pupils.yml", cleanAfter = true, executorId = "system")
  public void getSingleById()
  {
    //given a class

    //when
    final SchoolClassDto schoolClass = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-get"))
        .when()
        .get("/classes/{id}", 1L)
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassDto.class);

    //then
    Assert.assertEquals(1L, (long) schoolClass.getId());
  }


  @Test
  public void verifyClassValidation()
  {
    given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-create"))
        .body("{\n" +
            "  \"grade\": \"11\",\n" +
            "  \"letter\": \"A\",\n" +
            "  \"headTeacher\": {\n" +
            "    \"id\": \"123\",\n" +
            "    \"name\": \"Иван Петрович\"\n" +
            "  }\n" +
            "}")
        .when()
        .post("/classes")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE);
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
  public void schoolClassCanBeCreatedWithoutATeacher()
  {
    final SchoolClassController.ClassCreated schoolClass = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-create"))
        .body("{\n" +
            "  \"grade\": \"11\",\n" +
            "  \"letter\": \"A\",\n" +
            "  \"year\": \"2018\" \n" +
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
  @DataSet(value = "datasets/classes-and-pupils.yml", cleanAfter = true, executorId = "system")
  public void schoolClassCanBeUpdated()
  {
    //given a class
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
        .put("/classes/{classId}", 1L)
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassDto.class);

    //then
    Assert.assertThat(schoolClass.getId(), Matchers.is(1L));
    Assert.assertThat(schoolClass.getGrade(), Matchers.is(11));
    Assert.assertThat(schoolClass.getLetter(), Matchers.is("A"));
    Assert.assertThat(schoolClass.getHeadTeacher().getId(), Matchers.is(123L));
    Assert.assertThat(schoolClass.getHeadTeacher().getName(), Matchers.is("Иван Петрович"));
  }

}
