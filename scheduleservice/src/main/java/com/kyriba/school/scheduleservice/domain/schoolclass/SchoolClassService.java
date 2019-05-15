package com.kyriba.school.scheduleservice.domain.schoolclass;

import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass find(int grade, String letter, int currentYear) {
        int foundationYear = grade == 1 ? currentYear : currentYear - grade;
        return Optional.ofNullable(schoolClassRepository.findByGradeAndLetterIgnoreCaseAndFoundationYear(grade, letter, foundationYear))
                .orElseThrow(() -> new SchoolClassNotFoundException(grade, letter, foundationYear));
    }


    public SchoolClass findBySchedule(Schedule schedule) {
        SchoolClass schoolClassToFind = schedule.getSchoolClass();
        int year = schedule.getYear();
        return find(schoolClassToFind.getGrade(), schoolClassToFind.getLetter(), year);
    }
}
