/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import com.kyriba.curriculum.domain.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author M-DBE
 */
@Entity(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subject_id")
  private long id;

  @Basic(optional = false)
  @Column(nullable = false)
  private String name;


  public SubjectEntity(String name)
  {
    this.name = name;
  }


  public SubjectDTO toSubjectDTO()
  {
    return new SubjectDTO(id, name);
  }
}
