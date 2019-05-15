package com.kyriba.school.scheduleservice.domain.lesson;

import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassRepository;
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
        SchoolClass schoolClass = schoolClassRepository.findById(lesson.getSchoolClass().getEntityId()).get();
        lesson.setSchoolClass(schoolClass);
        return lessonRepository.save(lesson);
    }
}
