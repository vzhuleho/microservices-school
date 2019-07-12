package com.kyriba.school.userservice.user;

import static io.restassured.RestAssured.*;

import com.zaxxer.hikari.HikariConfig;
import io.restassured.http.ContentType;
import java.net.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = FullPathTest.Initializer.class)
public class FullPathTest {

  @Autowired
  private HikariConfig dataSource;

  @ClassRule
  public static MySQLContainer mysql = new MySQLContainer();

  @Before
  public void before() {
    Assert.assertTrue(dataSource.getJdbcUrl().startsWith("jdbc:mysql"));
  }

  @Test
  public void testAddParent() throws Exception
  {
    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Alex\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"N/A\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}")
    .when()
        .post(URI.create("/parents"))
    .then()
        .statusCode(201);
  }

  @Test
  public void testAddPrincipal() throws Exception
  {
    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Max\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"N/A\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}")
        .when()
        .post(URI.create("/principals"))
        .then()
        .statusCode(201);
  }

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + mysql.getJdbcUrl(),
          "spring.datasource.username=" + mysql.getUsername(),
          "spring.datasource.password=" + mysql.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}
