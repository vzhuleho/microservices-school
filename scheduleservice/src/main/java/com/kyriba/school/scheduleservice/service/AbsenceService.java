package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.AbsenceRepository;
import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceDetails;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceRequest;
import com.kyriba.school.scheduleservice.domain.entity.Absence;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.Pupil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final PupilRepository pupilRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public void removeAbsenceById(Long id) {
        absenceRepository.deleteById(id);
    }

    @Transactional
    public AbsenceDetails updateAbsence(AbsenceRequest absenceRequest) {
        Absence absence = findByIdOrElseThrowNotFound(absenceRequest.getId()).applyData(absenceRequest);
        return absenceRepository.save(absence).output();
    }

    public AbsenceDetails getAbsenceById(Long id) {
        return findByIdOrElseThrowNotFound(id).output();
    }

    private Absence findByIdOrElseThrowNotFound(Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new AbsenceNotFound(id));
    }

    @Transactional
    public Long addAbsenceToLesson(AbsenceRequest absenceRequest) {
        Lesson lesson = lessonRepository.findById(absenceRequest.getLessonId())
            .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.findById(absenceRequest.getPupilId())
                .orElseThrow(() -> new PupilNotFoundException(absenceRequest.getPupilId()));
        return absenceRepository.save(new Absence(absenceRequest.getReason(), pupil, lesson)).getId();
    }

    class AbsenceNotFound extends ResourceNotFoundException {
        AbsenceNotFound(Long id) {
            super(String.format("Absence %d doesn't exist", id));
        }
    }

}
