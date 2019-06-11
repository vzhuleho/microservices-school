package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.dto.SubjectDTO;
import com.kyriba.school.scheduleservice.domain.dto.TeacherDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class LessonControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String SCHEDULE_LESSONS = "/api/v1/schedules/2019/10/Z/days/2019-09-01/lessons";
	private static final String LETTER = "Z";
	private static final int GRADE = 10;

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	public void getLessonsByScheduleIdAndDate() {
		LessonDTO[] lessons = given(documentationSpec)
				.filter(document("schedule-day-lessons-list"))
				.when()
				.get(SCHEDULE_LESSONS)
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO[].class);
		assertEquals(LocalDate.parse("2019-09-01"), lessons[0].getDate());
		assertEquals(1, lessons[0].getIndex());
	}

	@Test
	public void getLessonByNumber() {
		LessonDTO lesson = given(documentationSpec)
				.filter(document("schedule-day-lessons-get"))
				.when()
				.get(SCHEDULE_LESSONS + "/2")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);
		assertEquals(2, lesson.getIndex());
		assertEquals(LocalDate.parse("2019-09-01"), lesson.getDate());
		SchoolClassDTO schoolClass = lesson.getSchoolClass();
		assertEquals(GRADE, schoolClass.getGrade());
		assertEquals(LETTER, schoolClass.getLetter());
	}

	@Test
	public void updateLesson() throws JSONException {
		String expectedNote = "Some note";
		JSONObject lessonAsJson = new JSONObject(new LessonDTO(), true);
		lessonAsJson.put("date", "2018-09-15");
		lessonAsJson.put("index", 1);
		lessonAsJson.put("subject", new JSONObject(new SubjectDTO()));
		lessonAsJson.put("teacher", new JSONObject(new TeacherDTO()));
		lessonAsJson.put("schoolClass", new JSONObject(new SchoolClassDTO()));
		lessonAsJson.put("note", expectedNote);

		LessonDTO lesson = given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lessons-update"))
				.when()
				.body(lessonAsJson.toString())
				.put(SCHEDULE_LESSONS + "/2")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);

		assertEquals(expectedNote, lesson.getNote());
	}

	@Test
	public void addAbsenceToLesson() throws JSONException {
		JSONObject absenceAsJson = new JSONObject();
		absenceAsJson.put("pupilName", "Ivan Ivanov");

		long id = given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-add"))
				.when()
				.body(absenceAsJson.toString())
				.post(SCHEDULE_LESSONS + "/2/absences")
				.then()
				.statusCode(SC_CREATED)
				.extract().as(long.class);
		assertEquals(1, id);
	}

	@Test
	public void deleteAbsenceById() {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-absences-delete"))
				.when()
				.delete(SCHEDULE_LESSONS + "/2/absences/1")
				.then()
				.statusCode(SC_NO_CONTENT);
	}

	@Test
	public void addMarkToLesson() throws JSONException {
		JSONObject markAsJson = new JSONObject();
		markAsJson.put("pupilName", "Sergei Sergeev");
		markAsJson.put("mark", 4);

		long id = given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-add"))
				.when()
				.body(markAsJson.toString())
				.post(SCHEDULE_LESSONS + "/3/marks")
				.then()
				.statusCode(SC_CREATED)
				.extract().as(long.class);
		assertEquals(1, id);
	}

	@Test
	public void deleteMarkById() {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedule-day-lesson-marks-delete"))
				.when()
				.delete(SCHEDULE_LESSONS + "/2/marks/1")
				.then()
				.statusCode(SC_NO_CONTENT);
	}
}
