package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vitaly Belkevich
 */
public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long>{
}
