/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.CourseToUpdate;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.CurriculumToCreate;
import com.kyriba.curriculum.domain.dto.Subject;
import com.kyriba.curriculum.domain.entity.CourseEntity;
import com.kyriba.curriculum.domain.entity.CurriculumEntity;
import com.kyriba.curriculum.domain.entity.CurriculumRepository;
import com.kyriba.curriculum.domain.entity.SubjectEntity;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumAlreadyExistsException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author M-DBE
 */
@Service
@Profile("!test")
@Transactional
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
  public List<BriefCurriculum> findAllCurricula()
  {
    return StreamSupport.stream(curriculumRepository.findAll().spliterator(), false)
        .map(curriculumEntity -> new BriefCurriculum(curriculumEntity.getId(), curriculumEntity.getGrade()))
        .collect(Collectors.toList());
  }


  @Override
  public Optional<Curriculum> findCurriculumByGrade(Integer grade)
  {
    return curriculumRepository.findByGrade(grade)
        .map(CurriculumServiceImpl::to);
  }


  @Override
  public Curriculum getCurriculumById(long id)
  {
    return curriculumRepository.findById(id)
        .map(CurriculumServiceImpl::to)
        .orElseThrow(() -> new CurriculumNotFoundException(id));

  }


  @Override
  public BriefCurriculum createCurriculum(CurriculumToCreate curriculumToCreate)
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
    curriculumRepository.deleteById(curriculumId);
    /*curriculumRepository.delete(curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId)));*/
  }


  @Override
  public Course addCourse(long curriculumId, CourseToAdd courseToAdd)
  {
    CurriculumEntity curriculumEntity = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    SubjectEntity subjectEntity = subjectRepository.findById(courseToAdd.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToAdd.getSubjectId()));

    CourseEntity courseEntity = new CourseEntity();
    courseEntity.setSubject(subjectEntity);
    courseEntity.setLessonCount(courseToAdd.getLessonCount());
    //courseEntity.setCurriculum(curriculumEntity);

    //return to(courseRepository.save(courseEntity));

    curriculumEntity.getCourses().add(courseEntity);
    CurriculumEntity savedCurriculum = curriculumRepository.save(curriculumEntity);

    return to(savedCurriculum.getCourses().stream()
        .filter(it -> it.getSubject().getId() == courseToAdd.getSubjectId())
        .findFirst()
        .orElseThrow(() -> new IllegalStateException())
    );
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
  public void updateCourse(long curriculumId, long courseId, CourseToUpdate courseToUpdate)
  {
    CurriculumEntity curriculum = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    CourseEntity course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));

    course.setLessonCount(courseToUpdate.getLessonCount());
    SubjectEntity subject = subjectRepository.findById(courseToUpdate.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToUpdate.getSubjectId()));
    course.setSubject(subject);

    curriculumRepository.save(curriculum);
  }


  private static Curriculum to(CurriculumEntity entity)
  {
    return new Curriculum(entity.getId(), entity.getGrade(), entity.getCourses().stream()
        .map(CurriculumServiceImpl::to)
        .collect(Collectors.toList()));
  }


  private static Course to(CourseEntity courseEntity)
  {
    return new Course(courseEntity.getId(), to(courseEntity.getSubject()), courseEntity.getLessonCount());
  }


  private static Subject to(SubjectEntity subjectEntity)
  {
    return new Subject(subjectEntity.getId(), subjectEntity.getName());
  }
}
