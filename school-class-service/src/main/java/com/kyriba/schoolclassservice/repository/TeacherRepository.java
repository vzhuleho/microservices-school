package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vitaly Belkevich
 */
@Repository
public interface TeacherRepository extends JpaRepository<HeadTeacherEntity, Long>{
}
