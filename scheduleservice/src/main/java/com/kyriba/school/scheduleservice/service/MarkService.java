package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.MarkRepository;
import com.kyriba.school.scheduleservice.domain.entity.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    private final MarkRepository markRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public MarkService(MarkRepository markRepository, LessonRepository lessonRepository) {
        this.markRepository = markRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<Mark> findByLessonId(long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(ResourceNotFoundException::new).getMarks();
    }

}
