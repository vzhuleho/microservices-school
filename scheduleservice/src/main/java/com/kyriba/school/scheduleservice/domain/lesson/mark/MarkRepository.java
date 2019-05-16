package com.kyriba.school.scheduleservice.domain.lesson.mark;

import com.kyriba.school.scheduleservice.domain.lesson.absence.Absence;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MarkRepository extends PagingAndSortingRepository<Mark, Long> {

}
