package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.AbsenceRepository;
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
