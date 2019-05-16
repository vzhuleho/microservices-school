package com.kyriba.school.scheduleservice.domain.lesson.absence;

import com.kyriba.school.scheduleservice.domain.lesson.Lesson;
import com.kyriba.school.scheduleservice.domain.lesson.LessonRepository;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

    @Autowired
    private AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

}
