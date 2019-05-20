/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 7.5.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.kyriba.schoolclassservice.service.SchoolClassService;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/v1/classes", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchoolClassController
{

  private final SchoolClassService service;


  @Autowired
  public SchoolClassController(SchoolClassService service)
  {
    this.service = service;
  }


  @ApiOperation(value = "Get all school classes")
  @GetMapping
  @ResponseBody
  public List<SchoolClassDto> get()
  {
    return service.getAll();
  }


  @ApiOperation(value = "Get school class by id")
  @GetMapping(value = "/{classId}")
  @ResponseBody
  public SchoolClassDto get(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                            @PathVariable Long classId)
  {
    return service.getById(classId);
  }


  @ApiOperation(value = "Register a new school class")
  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public SchoolClassDto add(@RequestBody SchoolClassDto schoolClass)
  {
    schoolClass.setId(null);
    return service.create(schoolClass);
  }


  @ApiOperation(value = "Update an exiting school class")
  @PutMapping(value = "/{classId}")
  public SchoolClassDto update(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                               @PathVariable Long classId,
                               @RequestBody SchoolClassDto schoolClass)
  {
    schoolClass.setId(classId);
    return service.updateClass(classId, schoolClass);
  }


  @ApiOperation(value = "View pupils in the class")
  @GetMapping(value = "/{classId}/pupils")
  @ResponseBody
  public Set<PupilDto> getPupils(@PathVariable Long classId)
  {
    return service.getPupilsForClass(classId);
  }


  @ApiOperation(value = "Add a pupil to the school class")
  @PutMapping(value = "/{classId}/pupils")
  @ResponseBody
  public Set<PupilDto> addPupilToClass(@ApiParam(value = "Class unique identifier", example = "1", required = true)
                                       @PathVariable Long classId,
                                       @RequestBody PupilDto pupil)
  {
    return service.addPupilToClass(classId, pupil);
  }


  @ApiOperation(value = "Remove a pupil from the school class")
  @DeleteMapping("/{classId}/pupils/{pupilId}")
  public void removePupilFromClass(
      @ApiParam(value = "Class unique identifier", example = "1", required = true)
      @PathVariable Long classId,
      @ApiParam(value = "Pupil unique identifier", example = "2", required = true)
      @PathVariable Long pupilId)
  {
    service.removePupilFromClass(classId, pupilId);
  }

}
