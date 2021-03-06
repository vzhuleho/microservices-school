package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
