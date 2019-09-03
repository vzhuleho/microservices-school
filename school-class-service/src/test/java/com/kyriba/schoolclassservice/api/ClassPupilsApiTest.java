/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 6.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.schoolclassservice.repository.PupilRepository;
import com.kyriba.schoolclassservice.repository.SchoolClassRepository;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-VBE
 * @since 19.2
 */
@ActiveProfiles("testcontainer")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@DBRider
@DataSet(value = "datasets/classes-and-pupils.yml", cleanAfter = true, executorId = "pupils")
public class ClassPupilsApiTest
{
  @LocalServerPort
  private int port;

  @Value("${api.version.path}")
  private String apiPrefix;


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
  public void verifyPupilValidation()
  {
    given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-pupils-add"))
        .body("{\n" +
            "    \"id\": \"123\"\n" +
            "  }\n")
        .when()
        .put("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE);
  }


  @Test
  public void pupilsCanBeRetrieved()
  {
    final List<PupilDto> pupilDtos = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-pupils-get"))
        .when()
        .get("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .getList("", PupilDto.class);

    Assert.assertThat(pupilDtos, Matchers.hasSize(2));
    Assert.assertThat(pupilDtos, Matchers.containsInAnyOrder(
        Matchers.hasProperty("name", Matchers.is("Иванов")),
        Matchers.hasProperty("name", Matchers.is("Петров"))));
  }


  @Test
  public void pupilCanBeAdded()
  {
    final SchoolClassController.PupilAdded pupilAdded = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-pupils-add"))
        .body("{\n" +
            "    \"id\": \"123\",\n" +
            "    \"name\": \"Иван Петрович\"\n" +
            "  }\n")
        .when()
        .put("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassController.PupilAdded.class);

    Assert.assertThat(pupilAdded.getId(), Matchers.is(123L));

    //Verify that pupil was really added
    final List<PupilDto> pupilDtos = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .when()
        .get("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .getList("", PupilDto.class);

    Assert.assertThat(pupilDtos, Matchers.hasSize(3));
    Assert.assertThat(pupilDtos, Matchers.containsInAnyOrder(
        Matchers.hasProperty("name", Matchers.is("Иванов")),
        Matchers.hasProperty("name", Matchers.is("Петров")),
        Matchers.hasProperty("name", Matchers.is("Иван Петрович"))));
  }


  @Test
  public void pupilCanBeAssignedToClass()
  {
    final SchoolClassController.PupilAdded pupilAdded = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-pupils-add"))
        .body("{\n" +
            "    \"id\": \"3\",\n" +
            "    \"name\": \"Сидоров\"\n" +
            "  }\n")
        .when()
        .put("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .as(SchoolClassController.PupilAdded.class);

    Assert.assertThat(pupilAdded.getId(), Matchers.is(3L));

    //Verify that pupil was really added
    final List<PupilDto> pupilDtos = given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .when()
        .get("/classes/1/pupils")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .getList("", PupilDto.class);

    Assert.assertThat(pupilDtos, Matchers.hasSize(3));
    Assert.assertThat(pupilDtos, Matchers.containsInAnyOrder(
        Matchers.hasProperty("name", Matchers.is("Иванов")),
        Matchers.hasProperty("name", Matchers.is("Петров")),
        Matchers.hasProperty("name", Matchers.is("Сидоров"))));
  }


  @Test
  public void pupilCanBeRemoved()
  {
    given(requestSpecification)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("school-class-pupils-delete"))
        .when()
        .delete("/classes/1/pupils/1")
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
