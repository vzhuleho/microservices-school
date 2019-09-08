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
import com.kyriba.curriculum.domain.entity.CourseEntity;
import com.kyriba.curriculum.domain.entity.CurriculumEntity;
import com.kyriba.curriculum.domain.entity.CurriculumRepository;
import com.kyriba.curriculum.domain.entity.SubjectEntity;
import com.kyriba.curriculum.domain.entity.SubjectRepository;
import com.kyriba.curriculum.service.exception.CourseNotFoundException;
import com.kyriba.curriculum.service.exception.CurriculumForGradeNotImplementedException;
import com.kyriba.curriculum.service.exception.CurriculumNotFoundException;
import com.kyriba.curriculum.service.exception.SubjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
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
        .map(CurriculumEntity::toBriefCurriculumDTO)
        .collect(Collectors.toList());
  }


  @Override
  public CurriculumDTO getCurriculumByGrade(int grade)
  {
    return curriculumRepository.findByGrade(grade)
        .map(CurriculumEntity::toCurriculumDTO)
        .orElseThrow(() -> new CurriculumForGradeNotImplementedException(grade));
  }


  @Override
  public CurriculumDTO getCurriculumById(long id)
  {
    return curriculumRepository.findById(id)
        .map(CurriculumEntity::toCurriculumDTO)
        .orElseThrow(() -> new CurriculumNotFoundException(id));

  }


  @Override
  public BriefCurriculumDTO createCurriculum(CurriculumToCreateDTO curriculumToCreate)
  {
    var curriculumEntity = new CurriculumEntity();
    curriculumEntity.setGrade(curriculumToCreate.getGrade());
    return curriculumRepository.save(curriculumEntity).toBriefCurriculumDTO();
  }


  @Override
  public void removeCurriculum(long curriculumId)
  {
    curriculumRepository.deleteById(curriculumId);
  }


  @Override
  public CourseDTO addCourse(long curriculumId, CourseToAddDTO courseToAdd)
  {
    var curriculumEntity = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    var subjectEntity = subjectRepository.findById(courseToAdd.getSubjectId())
        .orElseThrow(() -> new SubjectNotFoundException(courseToAdd.getSubjectId()));

    var courseEntity = new CourseEntity();
    courseEntity.setSubject(subjectEntity);
    courseEntity.setLessonCount(courseToAdd.getLessonCount());

    curriculumEntity.getCourses().add(courseEntity);
    return curriculumRepository.save(curriculumEntity).getCourses().stream()
        .filter(it -> it.getSubject().getId() == courseToAdd.getSubjectId())
        .findFirst()
        .map(CourseEntity::toCourseDTO)
        .orElseThrow(() -> new IllegalStateException(String.format("Course with subject id %d couldn't be found!",
            courseToAdd.getSubjectId())));
  }


  @Override
  public void removeCourse(long curriculumId, long courseId)
  {
    var curriculum = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    var course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));
    curriculum.getCourses().remove(course);
    curriculumRepository.save(curriculum);
  }


  @Override
  public void updateCourse(long curriculumId, long courseId, CourseToUpdateDTO courseToUpdate)
  {
    var curriculum = curriculumRepository.findById(curriculumId)
        .orElseThrow(() -> new CurriculumNotFoundException(curriculumId));
    var course = curriculum.getCourses().stream()
        .filter(it -> it.getId() == courseId)
        .findFirst()
        .orElseThrow(() -> new CourseNotFoundException(curriculumId, courseId));

    course.setLessonCount(courseToUpdate.getLessonCount());
    subjectRepository.findById(courseToUpdate.getSubjectId()).ifPresentOrElse(course::setSubject,
        () -> { throw new SubjectNotFoundException(courseToUpdate.getSubjectId()); });

    curriculumRepository.save(curriculum);
  }
}
