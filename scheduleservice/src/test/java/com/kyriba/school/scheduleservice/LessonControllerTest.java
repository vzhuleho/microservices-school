package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.domain.lesson.Lesson;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;
import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class LessonControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final int YEAR = 2018;
	private static final String LETTER = "A";
	private static final int GRADE = 1;
	private int scheduleId;

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
		scheduleId = given()
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.when()
				.get(SCHEDULES + "/" + YEAR + "/"+ GRADE + "/" + LETTER)
				.jsonPath()
				.getInt("id");
	}

	@Test
	public void getLessonsByScheduleIdAndDate() {
		Lesson[] lessons = given(documentationSpec)
				.filter(document("schedule-day-lessons-list"))
				.when()
				.get(SCHEDULES + "/" + scheduleId + "/days/2018-09-01/lessons")
				.then()
				.statusCode(SC_OK)
				.extract().as(Lesson[].class);
		assertEquals(lessons.length, 8);
		assertEquals(LocalDate.parse("2018-09-01"), lessons[0].getDate());
		SchoolClass schoolClass = lessons[0].getSchoolClass();
		assertEquals(GRADE, schoolClass.getGrade());
		assertEquals(LETTER, schoolClass.getLetter());
	}

	@Test
	public void getLessonByNumber() {
		Lesson lesson = getLessonByPath(SCHEDULES + "/" + scheduleId + "/days/2018-09-15/lessons/2");
		assertEquals(2, lesson.getIndex());
		assertEquals(LocalDate.parse("2018-09-15"), lesson.getDate());
		SchoolClass schoolClass = lesson.getSchoolClass();
		assertEquals(GRADE, schoolClass.getGrade());
		assertEquals(LETTER, schoolClass.getLetter());
	}

	@Test
	public void updateLesson() throws JSONException {
		String expectedNote = "Some note";
		String path = SCHEDULES + "/" + scheduleId + "/days/2018-09-15/lessons/2";
		Lesson lesson = getLessonByPath(path);
		JSONObject lessonAsJson = new JSONObject(lesson, true);
		lessonAsJson.put("note", expectedNote);

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lessons-update"))
				.when()
				.body(lessonAsJson.toString())
				.patch(path)
				.then()
				.statusCode(SC_OK);

		lesson = getLessonByPath(path);
		assertEquals(expectedNote, lesson.getNote());
	}

	@Test
	public void addAbsenceToLesson() throws JSONException {
		String expectedPupilName = "Ivan Ivanov";
		String path = SCHEDULES + "/" + scheduleId + "/days/2018-09-15/lessons/2/absences";
		JSONObject absenceAsJson = new JSONObject();
		absenceAsJson.put("pupilName", expectedPupilName);

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-add"))
				.when()
				.body(absenceAsJson.toString())
				.post(path)
				.then()
				.statusCode(SC_CREATED)
				.body("pupilName", is(expectedPupilName));
	}

	@Test
	public void deleteAbsenceById() {
		String path = SCHEDULES + "/1/days/2018-09-15/lessons/2/absences/1";
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-delete"))
				.when()
				.delete(path)
				.then()
				.statusCode(SC_NO_CONTENT);
	}

	@Test
	public void addMarkToLesson() throws JSONException {
		String expectedPupilName = "Sergei Sergeev";
		int expectedMark = 4;
		String path = SCHEDULES + "/" + scheduleId + "/days/2018-09-15/lessons/3/marks";
		JSONObject markAsJson = new JSONObject();
		markAsJson.put("pupilName", expectedPupilName);
		markAsJson.put("mark", expectedMark);

		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-add"))
				.when()
				.body(markAsJson.toString())
				.post(path)
				.then()
				.statusCode(SC_CREATED)
				.body("pupilName", is(expectedPupilName))
				.body("mark", is(expectedMark));
	}

	@Test
	public void deleteMarkById() {
		String path = SCHEDULES + "/1/days/2018-09-15/lessons/2/marks/1";
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-delete"))
				.when()
				.delete(path)
				.then()
				.statusCode(SC_NO_CONTENT);
	}

	private Lesson getLessonByPath(String path) {
		return given(documentationSpec)
				.filter(document("schedule-day-lessons-get"))
				.when()
				.get(path)
				.then()
				.statusCode(SC_OK)
				.extract().as(Lesson.class);
	}
}
