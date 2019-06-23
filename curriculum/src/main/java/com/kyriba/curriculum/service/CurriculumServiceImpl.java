/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculumDTO;
import com.kyriba.curriculum.domain.dto.CourseDTO;
import com.kyriba.curriculum.domain.dto.CourseToAddDTO;
import com.kyriba.curriculum.domain.dto.CourseToUpdateDTO;
import com.kyriba.curriculum.domain.dto.CurriculumDTO;
import com.kyriba.curriculum.domain.dto.CurriculumToCreateDTO;
import com.kyriba.curriculum.domain.dto.SubjectDTO;
import com.kyriba.curriculum.domain.entity.CourseEntity;
import com.kyriba.curriculum.domain.entity.CurriculumEntity;
import com.kyriba.curriculum.domain.entity.CurriculumRepository;
import com.kyriba.curriculum.domain.entity.SubjectEntity;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.CourseAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author M-DBE
 */
@Service
@Transactional
@Validated
class CurriculumServiceImpl implements CurriculumService
{
  private static final Logger logger = LoggerFactory.getLogger(CurriculumServiceImpl.class);

  private final CurriculumRepository curriculumRepository;
  private final SubjectRepository subjectRepository;


  @Autowired
  CurriculumServiceImpl(CurriculumRepository curriculumRepository,
                        SubjectRepository subjectRepository)
  {
    this.curriculumRepository = curriculumRepository;
    this.subjectRepository = subjectRepository;
  }


  @Override
  public List<BriefCurriculumDTO> findAllCurricula()
  {
    return StreamSupport.stream(curriculumRepository.findAll().spliterator(), false)
        .map(curriculumEntity -> new BriefCurriculumDTO(curriculumEntity.getId(), curriculumEntity.getGrade()))
        .collect(Collectors.toList());
  }


  @Override
  public Optional<CurriculumDTO> findCurriculumByGrade(Integer grade)
  {
    return curriculumRepository.findByGrade(grade)
        .map(CurriculumServiceImpl::to);
  }


  @Override
  public CurriculumDTO getCurriculumById(long id)
  {
    return curriculumRepository.findById(id)
        .map(CurriculumServiceImpl::to)
        .orElseThrow(() -> new CurriculumNotFoundException(id));

  }


  @Override
  public BriefCurriculumDTO createCurriculum(CurriculumToCreateDTO curriculumToCreate)
  {
    try {
      CurriculumEntity curriculumEntity = new CurriculumEntity();
      curriculumEntity.setGrade(curriculumToCreate.getGrade());
      curriculumRepository.save(curriculumEntity);
      return to(curriculumEntity).toBrief();
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Curriculum with grade %d can't be created!", curriculumToCreate.getGrade()), e);
      throw new CurriculumAlreadyExistsException(curriculumToCreate.getGrade());
    }
  }


  @Override
  public void removeCurriculum(long curriculumId)
  {
    try {
      curriculumRepository.deleteById(curriculumId);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Curriculum with id %d doesn't exist!", curriculumId), e);
      throw new CurriculumNotFoundException(curriculumId);
    }
  }


  @Override
  public CourseDTO addCourse(long curriculumId, CourseToAddDTO courseToAdd)
  {
    CurriculumEntity curriculumEntity = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    SubjectEntity subjectEntity = subjectRepository.findById(courseToAdd.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToAdd.getSubjectId()));

    CourseEntity courseEntity = new CourseEntity();
    courseEntity.setSubject(subjectEntity);
    courseEntity.setLessonCount(courseToAdd.getLessonCount());

    curriculumEntity.getCourses().add(courseEntity);
    CurriculumEntity savedCurriculum = curriculumRepository.save(curriculumEntity);

    try {
      return to(savedCurriculum.getCourses().stream()
          .filter(it -> it.getSubject().getId() == courseToAdd.getSubjectId())
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(String.format("Course with subject id %d couldn't be found!",
              courseToAdd.getSubjectId())))
      );
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Course already exists for curriculum %d and subject %d!",
          curriculumId, courseToAdd.getSubjectId()), e);
      throw new CourseAlreadyExistsException(curriculumId, courseToAdd.getSubjectId());
    }
  }


  @Override
  public void removeCourse(long curriculumId, long courseId)
  {
    CurriculumEntity curriculum = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    CourseEntity course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));
    curriculum.getCourses().remove(course);
    curriculumRepository.save(curriculum);
  }


  @Override
  public void updateCourse(long curriculumId, long courseId, CourseToUpdateDTO courseToUpdate)
  {
    CurriculumEntity curriculum = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    CourseEntity course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));

    course.setLessonCount(courseToUpdate.getLessonCount());
    subjectRepository.findById(courseToUpdate.getSubjectId()).ifPresentOrElse(course::setSubject,
        () -> { throw new SubjectNotFoundException(courseToUpdate.getSubjectId()); });

    try {
      curriculumRepository.save(curriculum);
    }
    catch (DataIntegrityViolationException e) {
      logger.error(String.format("Course already exists for curriculum %d and subject %d!",
          curriculumId, courseToUpdate.getSubjectId()), e);
      throw new CourseAlreadyExistsException(curriculumId, courseToUpdate.getSubjectId());
    }
  }


  private static CurriculumDTO to(CurriculumEntity entity)
  {
    return new CurriculumDTO(entity.getId(), entity.getGrade(), entity.getCourses().stream()
        .map(CurriculumServiceImpl::to)
        .collect(Collectors.toList()));
  }


  private static CourseDTO to(CourseEntity courseEntity)
  {
    return new CourseDTO(courseEntity.getId(), to(courseEntity.getSubject()), courseEntity.getLessonCount());
  }


  private static SubjectDTO to(SubjectEntity subjectEntity)
  {
    return new SubjectDTO(subjectEntity.getId(), subjectEntity.getName());
  }
}
