package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.api.MarkController;
import com.kyriba.school.scheduleservice.service.MarkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MarkController.class)
@ActiveProfiles("test")
public class MarkRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MarkService markService;

	@Test
	public void add_mark_less_than_1() throws Exception {
		mockMvc.perform(post("/api/v1/marks")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 0, \"lessonId\": 1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void add_mark_greater_than_10() throws Exception {
		mockMvc.perform(post("/api/v1/marks")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 11, \"lessonId\": 1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void add_valid_mark() throws Exception {
		mockMvc.perform(post("/api/v1/marks")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 5, \"lessonId\": 1}"))
				.andExpect(status().isCreated());
	}

	@Test
	public void update_mark_less_than_1() throws Exception {
		mockMvc.perform(put("/api/v1/marks/1")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 0, \"lessonId\": 1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void update_mark_greater_than_10() throws Exception {
		mockMvc.perform(put("/api/v1/marks/1")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 11, \"lessonId\": 1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void update_mark_with_valid_value() throws Exception {
		mockMvc.perform(put("/api/v1/marks/1")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": 1, \"value\": 7, \"lessonId\": 1}"))
				.andExpect(status().isOk());
	}
}
