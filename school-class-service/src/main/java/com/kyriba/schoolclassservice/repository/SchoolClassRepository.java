package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vitaly Belkevich
 */
@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long>{
}
