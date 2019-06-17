package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
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
import static io.restassured.RestAssured.with;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class AbsenceControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RequestSpecification documentationSpec;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
                .setBasePath("/api/v1/absences")
                .addFilter(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void testAbsencesCRUD() throws JSONException {
        // Given
        String pupilName = "Uasya";
        String reason = "illness";
        long lessonId = with().contentType(APPLICATION_JSON_UTF8_VALUE)
            .get("/api/v1/lessons/2019/10/Z/2019-09-01")
            .as(LessonDTO[].class)[0].getId();
        // When add
        JSONObject absenceAsJson = new JSONObject();
        absenceAsJson.put("pupilName", pupilName);
        absenceAsJson.put("reason", reason);
        absenceAsJson.put("lessonId", lessonId);
        long absenceId = given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("absences-add"))
            .when()
            .body(absenceAsJson.toString())
            .post()
            .then()
            .statusCode(SC_CREATED)
            .extract().as(long.class);
        // When get
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("absences-get"))
            .when()
            .get("/" + absenceId)
            .then()
            .statusCode(SC_OK)
            .body("pupilName", is(pupilName))
            .body("reason", is(reason));
        // When update
        String reasonNew = "hangover";
        absenceAsJson.put("id", absenceId);
        absenceAsJson.put("reason", reasonNew);
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("absences-update"))
            .when()
            .body(absenceAsJson.toString())
            .put("/" + absenceId)
            .then()
            .statusCode(SC_OK)
            .body("reason", is(reasonNew));
        // When delete
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("absences-delete"))
            .when()
            .delete("/" + absenceId)
            .then()
            .statusCode(SC_NO_CONTENT);
    }
}
