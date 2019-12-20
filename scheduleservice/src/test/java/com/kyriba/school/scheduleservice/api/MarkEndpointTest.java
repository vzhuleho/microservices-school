package com.kyriba.school.scheduleservice.api;

import com.kyriba.school.scheduleservice.domain.dto.LessonDetails;
import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
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
public class MarkEndpointTest {

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
    public void testMarksCRUD() throws JSONException {
        // Given
        int value = 2;
        String note = "Terrible";
        LessonDetails lesson = with().contentType(APPLICATION_JSON_UTF8_VALUE)
            .get("/api/v1/lessons/2019/10/Z/2019-09-01")
            .as(LessonDetails[].class)[0];
        long lessonId = lesson.getId();
        PupilDetails pupilDetails = lesson.getSchoolClass().getPupils().get(0);
        // When add
        JSONObject markAsJson = new JSONObject();
        markAsJson.put("pupilId", pupilDetails.getId());
        markAsJson.put("value", value);
        markAsJson.put("note", note);
        markAsJson.put("lessonId", lessonId);
        long markId = given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("marks-add"))
            .when()
            .body(markAsJson.toString())
            .post()
            .then()
            .statusCode(SC_CREATED)
            .extract().as(long.class);
        // When get
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("marks-get"))
            .when()
            .get("/" + markId)
            .then()
            .statusCode(SC_OK)
            .body("pupil.name", is(pupilDetails.getName()))
            .body("value", is(value))
            .body("note", is(note));
        // When update
        int valueNew = 5;
        String noteNew = "note";
        markAsJson.put("id", markId);
        markAsJson.put("value", valueNew);
        markAsJson.put("note", noteNew);
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("marks-update"))
            .when()
            .body(markAsJson.toString())
            .put("/" + markId)
            .then()
            .statusCode(SC_OK)
            .body("value", is(valueNew))
            .body("note", is(noteNew));
        // When delete
        given(documentationSpec)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .filter(document("marks-delete"))
            .when()
            .delete("/1")
            .then()
            .statusCode(SC_NO_CONTENT);
    }
}
