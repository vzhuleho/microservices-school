package com.kyriba.school.scheduleservice;

import com.kyriba.school.scheduleservice.api.AbsenceController;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceRequest;
import com.kyriba.school.scheduleservice.service.AbsenceService;
import com.kyriba.school.scheduleservice.service.PupilNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AbsenceController.class)
@ActiveProfiles("test")
public class AbsenceRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AbsenceService absenceService;

	@Test
	public void add_absence_for_non_existent_pupil() throws Exception {

		doThrow(new PupilNotFoundException(1L))
				.when(absenceService).addAbsenceToLesson(any(AbsenceRequest.class));

		mockMvc.perform(post("/api/v1/absences")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"pupilId\": -999}"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string("A pupil with id [1] was not found"));
	}
}
