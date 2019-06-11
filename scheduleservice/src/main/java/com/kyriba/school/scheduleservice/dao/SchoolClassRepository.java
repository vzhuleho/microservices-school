package com.kyriba.school.scheduleservice.dao;

import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SchoolClassRepository extends CrudRepository<SchoolClass, Long> {

    SchoolClass findByGradeAndLetterIgnoreCaseAndFoundationYear(int grade, String letter, int year);

}