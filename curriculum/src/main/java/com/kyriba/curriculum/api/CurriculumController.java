/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.api;

import com.kyriba.curriculum.api.dto.BriefCurriculum;
import com.kyriba.curriculum.api.dto.Curriculum;
import com.kyriba.curriculum.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


/**
 * @author M-DBE
 */
@RestController
@RequestMapping("/curricula")
@RequiredArgsConstructor
public class CurriculumController
{
  private final CurriculumService curriculumService;


  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public List<BriefCurriculum> getAllCurricula()
  {
    return curriculumService.getAllCurricula();
  }


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Curriculum getCurriculumById(@PathVariable("id") long id)
  {
    return curriculumService.findCurriculumById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  @GetMapping(value = "/grades/{grade}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Curriculum getCurriculumByGrade(@Min(1) @Max(11) @PathVariable("grade") int grade)
  {
    return curriculumService.findCurriculumByGrade(grade)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED));
  }
}
