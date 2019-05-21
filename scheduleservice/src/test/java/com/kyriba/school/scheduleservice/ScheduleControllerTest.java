package com.kyriba.school.scheduleservice;

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

import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String SCHEDULES = "/api/v1/schedules";
	private static final int YEAR = 2018;
	private static final String LETTER = "A";
	private static final int GRADE = 1;

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	public void getOrCreate() {
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedules-get-or-create"))
				.when()
				.get(SCHEDULES + "/" + YEAR + "/1/A")
				.then()
				.statusCode(SC_OK)
				.body("year", is(YEAR))
				.body("schoolClass.grade", is(GRADE))
				.body("schoolClass.letter", is(LETTER));
	}

	@Test
	public void testScheduleCRUD() throws JSONException {

		// When create
		JSONObject schoolAsJson = new JSONObject();
		schoolAsJson.put("grade", GRADE);
		schoolAsJson.put("letter", LETTER);
		schoolAsJson.put("foundationYear", YEAR);
		JSONObject scheduleAsJson = new JSONObject();
		scheduleAsJson.put("year", YEAR);
		scheduleAsJson.put("schoolClass", schoolAsJson);

		int id = given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedules-create"))
				.when()
				.body(scheduleAsJson.toString())
				.post(SCHEDULES)
				.then()
				.statusCode(SC_CREATED)
				.body("year", is(YEAR))
				.body("schoolClass.grade", is(GRADE))
				.body("schoolClass.letter", is(LETTER))
				.extract()
				.jsonPath()
				.getInt("id");

		String pathToSchedule = SCHEDULES + "/" + id;

		// When get all
		given(documentationSpec)
				.filter(document("schedules-list"))
				.when()
				.get(SCHEDULES)
				.then()
				.statusCode(SC_OK);

		// When get one created
		given(documentationSpec)
				.filter(document("schedules-get"))
				.when()
				.get(pathToSchedule)
				.then()
				.statusCode(SC_OK)
				.body("year", is(YEAR))
				.body("schoolClass.grade", is(GRADE))
				.body("schoolClass.letter", is(LETTER));

		// Given
		int newExpectedYear = 2019;

		// When update
		scheduleAsJson.put("year", newExpectedYear);
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedules-update"))
				.when()
				.body(scheduleAsJson.toString())
				.put(SCHEDULES)
				.then()
				.statusCode(SC_OK)
				.body("year", is(newExpectedYear));

		// When deleteById
		given(documentationSpec)
				.contentType(APPLICATION_JSON_UTF8_VALUE)
				.filter(document("schedules-delete"))
				.when()
				.delete(pathToSchedule)
				.then()
				.statusCode(SC_NO_CONTENT);
	}

}
