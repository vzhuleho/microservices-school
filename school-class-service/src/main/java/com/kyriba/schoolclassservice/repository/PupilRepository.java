package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vitaly Belkevich
 */
@Repository
public interface PupilRepository extends JpaRepository<PupilEntity, Long>{
}
