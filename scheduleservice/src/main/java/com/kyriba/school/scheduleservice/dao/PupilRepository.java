package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Pupil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PupilRepository extends CrudRepository<Pupil, Long> {

}
