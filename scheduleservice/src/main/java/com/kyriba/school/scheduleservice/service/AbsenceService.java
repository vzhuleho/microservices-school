package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.AbsenceRepository;
import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
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
    public Long pupilIsAbsentAtLessonBecauseOf(Long pupilId, Long lessonId, String reason) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.findById(pupilId)
                .orElseThrow(ResourceNotFoundException::new);
        return absenceRepository.save(new Absence(reason, pupil, lesson)).id();
    }

    @Transactional
    public void removeAbsenceById(Long id) {
        absenceRepository.deleteById(id);
    }

    @Transactional
    public AbsenceDTO updateAbsence(AbsenceDTO absenceDTO) {
        Absence absence = findByIdOrElseThrowNotFound(absenceDTO.getId()).applyData(absenceDTO);
        return absenceRepository.save(absence).output();
    }

    public AbsenceDTO getAbsenceById(Long id) {
        return findByIdOrElseThrowNotFound(id).output();
    }

    private Absence findByIdOrElseThrowNotFound(Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new AbsenceNotFound(id));
    }

    @Transactional
    public Long addAbsenceToLesson(AbsenceDTO absenceDTO) {
        Lesson lesson = lessonRepository.findById(absenceDTO.getLessonId())
            .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.getByNameAndSchoolClass(absenceDTO.getPupilName(), lesson.getSchoolClass())
            .orElseThrow(ResourceNotFoundException::new);
        return absenceRepository.save(new Absence(absenceDTO.getReason(), pupil, lesson)).id();
    }

    @Transactional
    public void removeAbsenceFromLesson(Long absenceId) {
        absenceRepository.deleteById(absenceId);
    }

    class AbsenceNotFound extends ResourceNotFoundException {
        AbsenceNotFound(Long id) {
            super(String.format("Absence %d doesn't exist", id));
        }
    }

}
