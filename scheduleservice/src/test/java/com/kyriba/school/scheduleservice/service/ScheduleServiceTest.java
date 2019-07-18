package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @MockBean
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test (expected = SchoolClassNotFoundException.class)
    public void create_schoolClass_not_found() {
        doThrow(SchoolClassNotFoundException.class).when(schoolClassRepository).findById(anyLong());
        scheduleService.create(new ScheduleRequest(1L, 2019, 1L));
    }
}