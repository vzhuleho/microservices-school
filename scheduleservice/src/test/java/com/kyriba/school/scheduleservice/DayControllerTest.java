package com.kyriba.school.scheduleservice;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DayControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String SCHEDULES = "/api/v1/schedules";
	private static final Long SCHEDULE_ID = 1L;

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	public void getByScheduleId() {

		given(documentationSpec)
				.filter(document("schedule-days-list"))
				.when()
				.get(SCHEDULES + "/" + SCHEDULE_ID + "/days")
				.then()
				.statusCode(SC_OK)
				.body("_embedded.days", not(empty()));
	}

	@Test
	public void getDayByScheduleIdAndDate() {

		given(documentationSpec)
				.filter(document("schedule-days-get"))
				.when()
					.get(SCHEDULES + "/" + SCHEDULE_ID + "/days/2018-09-01")
				.then()
				.statusCode(SC_OK);
	}

}
