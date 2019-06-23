/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author M-DBE
 */
@Entity(name = "subject")
@Getter
@Setter
@NoArgsConstructor
public class SubjectEntity
{
  @Id
  @GeneratedValue
  private long id;

  @Basic(optional = false)
  @Column(nullable = false)
  private String name;


  public SubjectEntity(String name)
  {
    this.name = name;
  }
}
