package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    private LessonService(LessonRepository lessonRepository, SchoolClassRepository schoolClassRepository) {
        this.lessonRepository = lessonRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public Lesson save(Lesson lesson) {
        SchoolClass schoolClassToFind = lesson.getSchoolClass();
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassToFind.getId())
            .orElseThrow(() -> new SchoolClassNotFoundException(schoolClassToFind));
        lesson.setSchoolClass(schoolClass);
        return lessonRepository.save(lesson);
    }

}
