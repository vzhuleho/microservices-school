package com.kyriba.schoolclassservice.service.externalservices;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;

import java.util.Optional;

public interface UserServiceClient
{
  Optional<PupilDto> findById(PupilDto pupul);

  Optional<HeadTeacherDto> findById(HeadTeacherDto teacher);
}
