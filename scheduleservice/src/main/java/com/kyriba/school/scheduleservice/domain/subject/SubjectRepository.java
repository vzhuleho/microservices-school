package com.kyriba.school.scheduleservice.domain.subject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
