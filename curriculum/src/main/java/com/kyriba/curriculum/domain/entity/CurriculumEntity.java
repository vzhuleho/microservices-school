/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


/**
 * @author M-DBE
 */
@Entity(name = "curriculum")
@Setter
@Getter
public class CurriculumEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "curriculum_id")
  private long id;

  @Basic(optional = false)
  private int grade;


  //@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "curriculum_id")
  private List<CourseEntity> courses = new ArrayList<>();
}
