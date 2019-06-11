package com.kyriba.school.userservice.user.pupil;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PupilRepository extends CrudRepository<PupilEntity, Long> {

}
