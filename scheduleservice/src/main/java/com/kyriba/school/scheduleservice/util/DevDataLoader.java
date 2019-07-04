package com.kyriba.school.scheduleservice.util;

import com.kyriba.school.scheduleservice.dao.*;
import com.kyriba.school.scheduleservice.domain.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test"})
@AllArgsConstructor
public class DevDataLoader implements CommandLineRunner {

    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final LessonRepository lessonRepository;
    private final PupilRepository pupilRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public void run(String... args) {
        schoolClassRepository.save(new SchoolClass().setGrade(1).setLetter("A").setYear(2018));
        schoolClassRepository.save(new SchoolClass().setGrade(1).setLetter("A").setYear(2019));
        SchoolClass schoolClass3 = schoolClassRepository.save(new SchoolClass().setGrade(10).setLetter("Z").setYear(2009));
        Schedule schedule = scheduleRepository.save(new Schedule(2019, schoolClass3));

        Pupil uasya = pupilRepository.save(new Pupil(1L, "Uasya", schoolClass3));
        Pupil petya = pupilRepository.save(new Pupil(2L, "Petya", schoolClass3));
        schedule.getDays().get(0).getLessons().forEach(lesson -> lesson
                .addAbsence(new Absence("Reason", uasya, lesson))
                .addMark(new Mark(9, "Excellent", petya, lesson)));
        lessonRepository.saveAll(schedule.getDays().get(0).getLessons());

        teacherRepository.save(new Teacher(1L, "Ivanov Ivan", Teacher.Status.ACTIVE));
        subjectRepository.save(new Subject(1L, "Biology"));
    }
}
