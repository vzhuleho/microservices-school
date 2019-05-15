package com.kyriba.school.scheduleservice;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;
import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class DayControllerTest {

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
	public void getByScheduleId() {

		given(documentationSpec)
				.filter(document("schedule-days-list"))
				.when()
				.get(SCHEDULES + "/" + scheduleId + "/days?page=1&size=1")
				.then()
				.statusCode(SC_OK)
				.body("_embedded.days", not(empty()));
	}


	@Test
	public void getDayByScheduleIdAndDate() {


		given(documentationSpec)
				.filter(document("schedule-days-get"))
				.when()
				.get(SCHEDULES + "/" + scheduleId + "/days/2018-09-01")
				.then()
				.statusCode(SC_OK)
				.body("date", is("2018-09-01"))
				.body("schedule.id", is(scheduleId))
				.body("lessons", iterableWithSize(8));
	}

}
