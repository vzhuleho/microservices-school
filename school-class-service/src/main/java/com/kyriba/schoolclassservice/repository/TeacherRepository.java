package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vitaly Belkevich
 */
public interface TeacherRepository extends JpaRepository<HeadTeacherEntity, Long>{
}
