package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vitaly Belkevich
 */
public interface PupilRepository extends JpaRepository<PupilEntity, Long>{
}
