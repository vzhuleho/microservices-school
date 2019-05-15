package com.kyriba.school.scheduleservice.domain.teacher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
