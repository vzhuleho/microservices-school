package com.kyriba.school.scheduleservice.domain.schoolclass;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false, excerptProjection = SchoolClassProjection.class)
public interface SchoolClassRepository extends CrudRepository<SchoolClass, Long> {

    SchoolClass findByGradeAndLetterIgnoreCaseAndFoundationYear(int grade, String letter, int year);
}
