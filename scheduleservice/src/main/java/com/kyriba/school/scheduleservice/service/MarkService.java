package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.MarkRepository;
import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.Mark;
import com.kyriba.school.scheduleservice.domain.entity.Pupil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MarkService {

    private final MarkRepository markRepository;
    private final PupilRepository pupilRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public Long addMarkToLesson(MarkDTO markDTO) {
        Lesson lesson = lessonRepository.findById(markDTO.getLessonId())
            .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.getByNameAndSchoolClass(markDTO.getPupilName(), lesson.getSchoolClass())
            .orElseThrow(ResourceNotFoundException::new);
        return markRepository.save(new Mark(markDTO.getValue(), markDTO.getNote(), pupil, lesson)).id();
    }

    @Transactional
    public void removeMarkById(Long id) {
        if (markRepository.existsById(id)) {
            markRepository.deleteById(id);
        }
    }

    @Transactional
    public MarkDTO updateMark(MarkDTO markDTO) {
        Mark savedMark = markRepository.save(findByIdOrElseThrowNotFound(markDTO.getId()).applyData(markDTO));
        return savedMark.output();
    }

    public MarkDTO getMarkById(Long id) {
        Mark mark = findByIdOrElseThrowNotFound(id);
        return new MarkDTO(id, mark.pupil().name(), mark.value(), mark.note(), mark.lesson().getId());
    }

    private Mark findByIdOrElseThrowNotFound(Long id) {
        return markRepository.findById(id).orElseThrow(() -> new MarkNotFound(id));
    }

    class MarkNotFound extends ResourceNotFoundException {
        MarkNotFound(Long id) {
            super(String.format("Mark %d doesn't exist", id));
        }
    }
}
