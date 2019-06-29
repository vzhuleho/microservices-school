package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default", "test"})
public class UserServiceMockClient implements UserServiceClient
{

  @Override
  public Optional<PupilDto> findById(PupilDto pupul)
  {
    return Optional.ofNullable(pupul);
  }


  @Override
  public Optional<HeadTeacherDto> findById(HeadTeacherDto teacher)
  {
    return Optional.ofNullable(teacher);
  }

}
