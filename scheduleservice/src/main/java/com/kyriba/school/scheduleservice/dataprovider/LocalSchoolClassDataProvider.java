package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Profile("!container")
@Repository
class LocalSchoolClassDataProvider implements SchoolClassDataProvider {

    private final SchoolClassRepository schoolClassRepository;
    private final PupilRepository pupilRepository;
    private final ModelMapper mapper;

    @Override
    public List<SchoolClassDetails> getSchoolClasses() {
        return StreamSupport.stream(schoolClassRepository.findAll().spliterator(), true)
                .map(schoolClass -> mapper.map(schoolClass, SchoolClassDetails.class))
                .collect(Collectors.toList());
    }

    @Override
    public SchoolClassDetails getSchoolClass(long id) {
        return mapper.map(schoolClassRepository.findById(id), SchoolClassDetails.class);
    }

    @Override
    public List<PupilDetails> getPupilsBySchoolClass(long id) {
        return pupilRepository.findBySchoolClassId(id).parallelStream()
                .map(pupil -> mapper.map(pupil, PupilDetails.class))
                .collect(Collectors.toList());
    }
}
