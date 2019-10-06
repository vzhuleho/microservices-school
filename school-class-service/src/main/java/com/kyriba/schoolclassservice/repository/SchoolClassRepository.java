package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author Vitaly Belkevich
 */
public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long>
{
  List<SchoolClassEntity> findByIdIn(List<Long> ids);
}

