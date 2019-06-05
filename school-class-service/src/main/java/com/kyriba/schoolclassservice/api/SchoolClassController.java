/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 7.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.kyriba.schoolclassservice.service.SchoolClassService;
import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @author M-VBE
 * @since 19.2
 */
@Api(value = "School classes endpoint")
@RestController
@RequestMapping(value = "${api.version.path}/classes", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class SchoolClassController
{
  private final SchoolClassService service;


  @ApiOperation(value = "Get all school classes")
  @GetMapping
  private List<SchoolClassDto> list()
  {
    return service.getAll();
  }


  @ApiOperation(value = "Get school class by id")
  @GetMapping(value = "/{classId}")
  private SchoolClassDto get(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                             @PathVariable Long classId)
  {
    return service.getById(classId);
  }


  @ApiOperation(value = "Register a new school class")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  private ClassCreated add(@RequestBody SchoolClassDto schoolClass)
  {
    schoolClass.setId(null);
    return new ClassCreated(service.create(schoolClass).getId());
  }


  @ApiOperation(value = "Update an exiting school class")
  @PutMapping(value = "/{classId}")
  private SchoolClassDto update(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                                @PathVariable Long classId,
                                @RequestBody ClassUpdateRequest updateRequest)
  {
    return service.updateClass(classId, updateRequest);
  }


  @ApiOperation(value = "View pupils in the class")
  @GetMapping(value = "/{classId}/pupils")
  private Set<PupilDto> getPupils(@PathVariable Long classId)
  {
    return service.getPupilsForClass(classId);
  }


  @ApiOperation(value = "Add a pupil to the school class")
  @PutMapping(value = "/{classId}/pupils")
  private PupilAdded addPupilToClass(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                                     @PathVariable Long classId,
                                     @RequestBody PupilDto pupil)
  {
    return new PupilAdded(service.addPupilToClass(classId, pupil).getId());
  }


  @ApiOperation(value = "Remove a pupil from the school class")
  @DeleteMapping("/{classId}/pupils/{pupilId}")
  private void removePupilFromClass(
      @ApiParam(value = "Class unique identifier", example = "1", required = true)
      @PathVariable Long classId,
      @ApiParam(value = "Pupil unique identifier", example = "2", required = true)
      @PathVariable Long pupilId)
  {
    service.removePupilFromClass(classId, pupilId);
  }


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PupilAdded
  {
    long id;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ClassCreated
  {
    long id;
  }

}
