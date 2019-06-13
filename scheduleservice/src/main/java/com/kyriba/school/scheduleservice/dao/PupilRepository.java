package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Pupil;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface PupilRepository extends CrudRepository<Pupil, Long> {
    Optional<Pupil> getByName(String pupilName);

    Optional<Pupil> getByNameAndSchoolClass(String pupilName, SchoolClass schoolClass);
}
