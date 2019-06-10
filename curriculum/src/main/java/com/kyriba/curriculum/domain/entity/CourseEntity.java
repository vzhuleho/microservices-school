/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * @author M-DBE
 */
@Entity(name = "course")
@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "curriculum_id", "subject_id" }) })
public class CourseEntity
{
  @Id
  @GeneratedValue
  @Column(nullable = false)
  private long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private SubjectEntity subject;

  @Basic(optional = false)
  @Column(nullable = false)
  private int lessonCount;


  /*@ManyToOne
  @JoinColumn
  private CurriculumEntity curriculum;*/
}
