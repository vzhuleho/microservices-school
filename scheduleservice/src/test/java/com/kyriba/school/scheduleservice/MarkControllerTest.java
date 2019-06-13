package com.kyriba.school.scheduleservice;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class MarkControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RequestSpecification documentationSpec;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
                .setBasePath("/api/v1/marks")
                .addFilter(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void getMark() {
        given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("mark-get"))
                .when()
                .get("/1")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void updateMark() {
        given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("mark-update"))
                .when()
                .body("{\n" +
                        "  \"pupilName\": \"Petya\",\n" +
                        "  \"value\": \"5\",\n" +
                        "  \"note\": \"note\"\n" +
                        "}")
                .put("/1")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void deleteMark() {
        given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("mark-delete"))
                .when()
                .delete("/1")
                .then()
                .statusCode(SC_NO_CONTENT);
    }
}
