package com.kyriba.school.userservice.user.parent;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends CrudRepository<ParentEntity, Long> {

  List<ParentEntity> findByIdIn(List<Long> ids);

}
