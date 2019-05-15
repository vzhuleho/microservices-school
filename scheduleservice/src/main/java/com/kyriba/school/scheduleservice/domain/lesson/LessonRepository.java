package com.kyriba.school.scheduleservice.domain.lesson;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
