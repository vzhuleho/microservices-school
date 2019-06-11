package com.kyriba.school.userservice.user.principal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends CrudRepository<PrincipalEntity, Long> {

}
