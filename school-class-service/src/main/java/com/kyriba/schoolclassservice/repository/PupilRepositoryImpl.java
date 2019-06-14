package com.kyriba.schoolclassservice.repository;

import com.kyriba.schoolclassservice.domain.PupilEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * @author Vitaly Belkevich
 */
public class PupilRepositoryImpl extends SimpleJpaRepository<PupilEntity, Long> implements PupilRepository {
  public PupilRepositoryImpl(JpaEntityInformation<PupilEntity, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
  }

  @Override
  public boolean existsById(Long aLong) {
    return super.existsById(aLong);
  }
}
