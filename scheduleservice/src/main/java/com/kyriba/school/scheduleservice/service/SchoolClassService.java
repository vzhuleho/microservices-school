package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
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
}
