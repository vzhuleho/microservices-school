/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import com.kyriba.curriculum.domain.dto.CourseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


/**
 * @author M-DBE
 */
@Entity(name = "course")
@Setter
@Getter
public class CourseEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private SubjectEntity subject;

  @Basic(optional = false)
  private int lessonCount;


  public CourseDTO toCourseDTO()
  {
    return new CourseDTO(id, subject.toSubjectDTO(), lessonCount);
  }
}
