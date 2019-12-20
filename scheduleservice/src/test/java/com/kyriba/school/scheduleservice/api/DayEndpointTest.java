package com.kyriba.school.scheduleservice.api;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DayEndpointTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private RequestSpecification documentationSpec;

	@LocalServerPort
	int port;

	private static final String SCHEDULE_DAYS = "/api/v1/schedules/2019/10/Z/days";

	@Before
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	public void getBySchedule() {

		given(documentationSpec)
				.filter(document("schedule-days-list"))
				.when()
				.get(SCHEDULE_DAYS)
				.then()
				.statusCode(SC_OK)
				.body("_embedded.days", not(empty()));
	}

	@Test
	public void getDayByScheduleAndDate() {

		given(documentationSpec)
				.filter(document("schedule-days-get"))
				.when()
				.get(SCHEDULE_DAYS + "/2019-09-01")
				.then()
				.statusCode(SC_OK);
	}

}
