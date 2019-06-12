package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.AbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

}
