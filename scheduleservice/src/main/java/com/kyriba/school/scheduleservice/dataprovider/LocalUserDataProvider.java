package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.dao.TeacherRepository;
import com.kyriba.school.scheduleservice.domain.dto.TeacherDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Profile("!container")
@Repository
class LocalUserDataProvider implements UserDataProvider {

    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    @Override
    public TeacherDetails getTeacher(long id) {
        return teacherRepository.findById(id).map(teacher -> mapper.map(teacher, TeacherDetails.class))
                .orElseThrow(ResourceNotFoundException::new);
    }
}
