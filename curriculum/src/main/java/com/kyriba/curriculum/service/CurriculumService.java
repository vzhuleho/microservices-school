/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.kyriba.curriculum.domain.dto.BriefCurriculum;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.CourseToAdd;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.Subject;

import java.util.List;
import java.util.Optional;


/**
 * @author M-DBE
 */
public interface CurriculumService
{
  Optional<Curriculum> findCurriculumById(long id);


  Optional<Curriculum> findCurriculumByGrade(int grade);


  List<BriefCurriculum> getAllCurricula();


  List<Subject> getAllSubjects();


  BriefCurriculum createCurriculum(int grade);


  BriefCurriculum removeCurriculum(long curriculumId);


  Course addCourse(long curriculumId, CourseToAdd course);


  Course updateCourse(long curriculumId, long courseId, int lessonCount);


  Course removeCourse(long curriculumId, long courseId);


  Subject createSubject(String name);
}
