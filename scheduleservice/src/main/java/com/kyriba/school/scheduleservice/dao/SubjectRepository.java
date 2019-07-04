package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
