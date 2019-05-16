/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.service;

import com.google.common.collect.Lists;
import com.kyriba.curriculum.domain.dto.Course;
import com.kyriba.curriculum.domain.dto.Curriculum;
import com.kyriba.curriculum.domain.dto.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;


/**
 * @author M-DBE
 */
public class NonProdData
{
  private static Subject ALGEBRA = new Subject(1000, "algebra");
  private static Subject GEOMETRY = new Subject(1001, "geometry");
  private static Subject ENGLISH = new Subject(1002, "english");
  private static Subject PHYSICS = new Subject(1003, "physics");


  public static final Supplier<List<Curriculum>> DEFAULT_CURRICULA = () -> Lists.newArrayList(
      new Curriculum(1, 11, Lists.newArrayList(
          new Course(100, ALGEBRA, 100),
          new Course(101, GEOMETRY, 100),
          new Course(102, ENGLISH, 100))
      ),
      new Curriculum(2, 10, Lists.newArrayList(
          new Course(103, ALGEBRA, 110),
          new Course(104, GEOMETRY, 90),
          new Course(105, ENGLISH, 80))
      )
  );
  public static List<Curriculum> CURRICULA = DEFAULT_CURRICULA.get();

  public static final Supplier<List<Subject>> DEFAULT_SUBJECTS = () -> new ArrayList<>(
      Arrays.asList(ALGEBRA, GEOMETRY, ENGLISH, PHYSICS));
  public static List<Subject> SUBJECTS = DEFAULT_SUBJECTS.get();

  static final AtomicLong COUNTER = new AtomicLong(10_000);


  private NonProdData()
  {

  }

}
