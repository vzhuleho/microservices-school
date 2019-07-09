package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.dao.LessonRepository;
import com.kyriba.school.scheduleservice.dao.MarkRepository;
import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.domain.dto.MarkDetails;
import com.kyriba.school.scheduleservice.domain.dto.MarkRequest;
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
    public Long addMarkToLesson(MarkRequest markRequest) {
        Lesson lesson = lessonRepository.findById(markRequest.getLessonId())
            .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.findById(markRequest.getPupilId()).orElseThrow(ResourceNotFoundException::new);
        return markRepository.save(new Mark(markRequest.getValue(), markRequest.getNote(), pupil, lesson)).id();
    }

    @Transactional
    public void removeMarkById(Long id) {
        if (markRepository.existsById(id)) {
            markRepository.deleteById(id);
        }
    }

    @Transactional
    public MarkDetails updateMark(MarkRequest marmarkRequest) {
        Mark savedMark = markRepository.save(findByIdOrElseThrowNotFound(marmarkRequest.getId()).applyData(marmarkRequest));
        return savedMark.output();
    }

    public MarkDetails getMarkById(Long id) {
        return findByIdOrElseThrowNotFound(id).output();
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
