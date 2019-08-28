package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.MarkRepository;
import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.domain.dto.MarkRequest;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class MarkServiceTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private PupilRepository pupilRepository;

    @Mock
    private LessonRepository lessonRepository;

    private MarkService markService;


    @Before
    public void setUp() {
        markService = new MarkService(markRepository, pupilRepository, lessonRepository);
    }

    // TODO: change to more specific exception
    @Test (expected = ResourceNotFoundException.class)
    public void addMarkToLesson_lesson_not_found() {
        doThrow(ResourceNotFoundException.class).when(lessonRepository).findById(anyLong());
        markService.addMarkToLesson(new MarkRequest(1L, 1L, 1, "", 1L));
    }

    // TODO: change to more specific exception
    @Test (expected = ResourceNotFoundException.class)
    public void addMarkToLesson_pupil_not_found() {
        doReturn(Optional.of(new Lesson())).when(lessonRepository).findById(anyLong());
        doThrow(ResourceNotFoundException.class).when(pupilRepository).findById(anyLong());
        markService.addMarkToLesson(new MarkRequest(1L, 1L, 1, "", 1L));
    }
}