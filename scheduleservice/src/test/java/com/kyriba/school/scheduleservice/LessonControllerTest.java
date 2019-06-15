package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.domain.dto.*;
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

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class LessonControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String LETTER = "Z";
	private static final int GRADE = 10;
	private static final int FOUNDATION_YEAR = 2009;
	private String FIRST_OF_SEPTEMBER = "2019-09-01";

	long lessonId;

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder()
				.setBasePath("/api/v1/lessons")
				.addFilter(documentationConfiguration(restDocumentation))
				.build();

		lessonId = with().contentType(APPLICATION_JSON_UTF8_VALUE)
				.get("/api/v1/lessons/2019/10/Z/2019-09-01")
				.as(LessonDTO[].class)[0].getId();
	}

	@Test
	public void getLessonsByScheduleIdAndDate() {
		LessonDTO[] lessons = given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lessons-list"))
				.when()
				.get("/2019/10/Z/2019-09-01")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO[].class);
		assertEquals(LocalDate.parse(FIRST_OF_SEPTEMBER), lessons[0].getDate());
		assertEquals(1, lessons[0].getIndex());
	}

	@Test
	public void updateLesson() throws JSONException {
		String expectedNote = "Some note";
		JSONObject lessonAsJson = new JSONObject(new LessonDTO(), true);
		lessonAsJson.put("id", lessonId);
		lessonAsJson.put("date", "2019-09-15");
		lessonAsJson.put("index", 1);
		lessonAsJson.put("subject", new JSONObject(new SubjectDTO()));
		lessonAsJson.put("teacher", new JSONObject(new TeacherDTO()));
		SchoolClassDTO schoolClassDTO = new SchoolClassDTO()
				.setGrade(GRADE)
				.setLetter(LETTER)
				.setFoundationYear(FOUNDATION_YEAR);
		lessonAsJson.put("schoolClass", new JSONObject(schoolClassDTO));
		lessonAsJson.put("note", expectedNote);

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lessons-update"))
				.when()
				.body(lessonAsJson.toString())
				.put("/" + lessonId)
				.then()
				.statusCode(SC_OK)
				.body("note", is(expectedNote));
	}

	@Test
	public void getAbsencesByLesson() {
        AbsenceDTO[] absences = given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("schedule-day-lesson-absences-list"))
                .when()
                .get("/" + lessonId + "/absences")
                .then()
                .statusCode(SC_OK)
                .extract().as(AbsenceDTO[].class);
        assertNotNull(absences);
        assertNotEquals(0, absences.length);
    }

    @Test
    public void getMarksForLesson() throws JSONException {
        MarkDTO[] marks = given(documentationSpec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("schedule-day-lesson-marks-list"))
                .when()
                .get("/" + lessonId + "/marks")
                .then()
                .statusCode(SC_OK)
                .extract().as(MarkDTO[].class);

        assertNotNull(marks);
        assertNotEquals(0, marks.length);

    }


}
