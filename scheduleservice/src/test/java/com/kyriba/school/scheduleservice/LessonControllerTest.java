package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.domain.dto.LessonDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.dto.SubjectDTO;
import com.kyriba.school.scheduleservice.domain.dto.TeacherDTO;
import com.kyriba.school.scheduleservice.domain.entity.Subject;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String SCHEDULES = "/api/v1/schedules";
	private static final String LETTER = "A";
	private static final int GRADE = 1;
	private static final Long SCHEDULE_ID = 1L;

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
				.get(SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-01/lessons")
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO[].class);
		assertEquals(LocalDate.parse("2018-09-01"), lessons[0].getDate());
		assertEquals(1, lessons[0].getIndex());
	}

	@Test
	public void getLessonByNumber() {
		LessonDTO lesson = getLessonByPath(SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-15/lessons/2");
		assertEquals(2, lesson.getIndex());
		assertEquals(LocalDate.parse("2018-09-15"), lesson.getDate());
		SchoolClassDTO schoolClass = lesson.getSchoolClass();
		assertEquals(GRADE, schoolClass.getGrade());
		assertEquals(LETTER, schoolClass.getLetter());
	}

	@Test
	public void updateLesson() throws JSONException {
		String expectedNote = "Some note";
		String path = SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-15/lessons/2";
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
				.put(path)
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);

		assertEquals(expectedNote, lesson.getNote());
	}

	@Test
	public void addAbsenceToLesson() throws JSONException {
		String expectedPupilName = "Ivan Ivanov";
		String path = SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-15/lessons/2/absences";
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
		String path = SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-15/lessons/3/marks";
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

	private LessonDTO getLessonByPath(String path) {
		return given(documentationSpec)
				.filter(document("schedule-day-lessons-get"))
				.when()
				.get(path)
				.then()
				.statusCode(SC_OK)
				.extract().as(LessonDTO.class);
	}
}
