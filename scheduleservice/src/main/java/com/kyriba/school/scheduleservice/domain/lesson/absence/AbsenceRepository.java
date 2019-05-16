package com.kyriba.school.scheduleservice.domain.lesson.absence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AbsenceRepository extends PagingAndSortingRepository<Absence, Long> {

}
