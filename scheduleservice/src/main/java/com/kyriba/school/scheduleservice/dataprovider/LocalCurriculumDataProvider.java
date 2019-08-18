package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.dao.SubjectRepository;
import com.kyriba.school.scheduleservice.domain.dto.SubjectDetails;
import com.kyriba.school.scheduleservice.domain.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Profile("!container")
@Repository
class LocalCurriculumDataProvider implements CurriculumDataProvider {

    private final SubjectRepository subjectRepository;
    private final ModelMapper mapper;


    @Override
    public SubjectDetails getSubject(long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return mapper.map(subject, SubjectDetails.class);
    }

    @Override
    public List<SubjectDetails> getSubjects() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), true)
                .map(subject -> mapper.map(subject, SubjectDetails.class))
                .collect(Collectors.toList());
    }
}
