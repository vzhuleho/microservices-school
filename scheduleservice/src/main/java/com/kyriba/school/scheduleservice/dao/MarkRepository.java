package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.Mark;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MarkRepository extends PagingAndSortingRepository<Mark, Long> {

}
