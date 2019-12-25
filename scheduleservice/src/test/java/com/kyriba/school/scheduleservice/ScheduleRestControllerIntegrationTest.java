package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.api.ScheduleController;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleDetails;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleRequest;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;
import com.kyriba.school.scheduleservice.service.ScheduleNotFoundException;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import com.kyriba.school.scheduleservice.service.SchoolClassNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
@ActiveProfiles("test")
public class ScheduleRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ScheduleService scheduleService;

	@Test
	public void get_schedule_by_year_grade_and_letter() throws Exception {
		SchoolClassDetails expectedSchoolClassDetails = new SchoolClassDetails()
				.setId(1L)
				.setYear(2019)
				.setGrade(1)
				.setLetter("A");
		ScheduleDetails expectedScheduleDetails = new ScheduleDetails(1L, 2019, expectedSchoolClassDetails);
		doReturn(expectedScheduleDetails).when(scheduleService).find(2019, 1, "A");


		mockMvc.perform(get("/api/v1/schedules/2019/1/A").accept("application/hal+json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
				.andExpect(jsonPath("year").value(2019))
				.andExpect(jsonPath("schoolClass").value(expectedSchoolClassDetails));
	}

	@Test
	public void get_schedule_not_found() throws Exception {
		doThrow(ScheduleNotFoundException.class)
				.when(scheduleService).find(2019, 1, "A");


		mockMvc.perform(get("/api/v1/schedules/2019/1/A").accept("application/hal+json"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void post_schedule_invalid_year() throws Exception {

		mockMvc.perform(post("/api/v1/schedules")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"id\": 1, \"year\": 0, \"schoolClassId\": 1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_schedule_schoolClass_not_found() throws Exception {

		doThrow(SchoolClassNotFoundException.class)
				.when(scheduleService).create(any(ScheduleRequest.class));

		//TODO: refactor exception to be more explicit and return bad request
		mockMvc.perform(post("/api/v1/schedules")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"id\": 1, \"year\": 2019, \"schoolClassId\": 1}"))
				.andExpect(status().isNotFound());
	}


}
