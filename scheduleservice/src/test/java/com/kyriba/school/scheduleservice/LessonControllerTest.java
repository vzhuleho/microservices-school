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
import static javax.servlet.http.HttpServletResponse.*;
import static org.junit.Assert.assertEquals;
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

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder()
				.setBasePath("/api/v1/lessons/2019/10/Z/2019-09-01/")
				.addFilter(documentationConfiguration(restDocumentation))
				.build();
	}

	@Test
	public void getLessonsByScheduleIdAndDate() {
		LessonDTO[] lessons = given(documentationSpec)
				.filter(document("schedule-day-lessons-list"))
				.when()
				.get()
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO[].class);
		assertEquals(LocalDate.parse(FIRST_OF_SEPTEMBER), lessons[0].getDate());
		assertEquals(1, lessons[0].getIndex());
	}

	@Test
	public void getLessonByNumber() {
		LessonDTO lesson = given(documentationSpec)
				.filter(document("schedule-day-lessons-get"))
				.when()
				.get("/2")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);
		assertEquals(2, lesson.getIndex());
		assertEquals(LocalDate.parse(FIRST_OF_SEPTEMBER), lesson.getDate());
		SchoolClassDTO schoolClass = lesson.getSchoolClass();
		assertEquals(GRADE, schoolClass.getGrade());
		assertEquals(LETTER, schoolClass.getLetter());
	}

	@Test
	public void updateLesson() throws JSONException {
		String expectedNote = "Some note";
		JSONObject lessonAsJson = new JSONObject(new LessonDTO(), true);
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

		LessonDTO lesson = given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lessons-update"))
				.when()
				.body(lessonAsJson.toString())
				.put("/2")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);

		assertEquals(expectedNote, lesson.getNote());
	}

	@Test
	public void addAbsenceToLesson() throws JSONException {
		JSONObject absenceAsJson = new JSONObject(new AbsenceDTO(), true);
		absenceAsJson.put("pupilName", "Ivan Ivanov");
		absenceAsJson.put("reason", "reason");

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-add"))
				.when()
				.body("{\n" +
						"  \"reason\":\"reason\",\n" +
						"  \"pupilName\":\"Ivan Ivanov\"\n" +
						"}")
				.post("/2/absences")
				.then()
				.statusCode(SC_CREATED)
				.extract().as(long.class);
	}

	@Test
	public void deleteAbsenceById() {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-delete"))
				.when()
				.delete("/2/absences/1")
				.then()
				.statusCode(SC_NO_CONTENT);
	}

	@Test
	public void addMarkToLesson() throws JSONException {
		JSONObject markAsJson = new JSONObject(new MarkDTO(), true);
		markAsJson.put("pupilName", "Sergei Sergeev");
		markAsJson.put("value", "4");
		markAsJson.put("note", "note");

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-add"))
				.when()
				.body(markAsJson.toString())
				.post("/3/marks")
				.then()
				.statusCode(SC_CREATED)
				.extract().as(long.class);
	}

	@Test
	public void getMarksForLesson() throws JSONException {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-get"))
				.when()
				.get()
				.then()
				.statusCode(SC_OK);
	}

	@Test
	public void deleteMarkById() {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-delete"))
				.when()
				.delete("/2/marks/1")
				.then()
				.statusCode(SC_NO_CONTENT);
	}
}
