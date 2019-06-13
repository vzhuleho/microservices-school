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
    public Long markPupilAtLesson(Long pupilId, Long lessonId, int value, String note) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(ResourceNotFoundException::new);
        Pupil pupil = pupilRepository.findById(pupilId)
                .orElseThrow(ResourceNotFoundException::new);
        return markRepository.save(new Mark(value, note, pupil, lesson)).id();
    }

    @Transactional
    public void removeMarkById(Long id) {
        markRepository.deleteById(id);
    }

    @Transactional
    public Long updateMarkById(Long id, MarkDTO markDTO) {
        return markRepository.save(
                findByIdOrElseThrowNotFound(id).applyData(markDTO)
        ).id();
    }

    public MarkDTO getMarkById(Long id) {
        Mark mark = findByIdOrElseThrowNotFound(id);
        return new MarkDTO(mark.pupil().name(), mark.value(), mark.note());
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
