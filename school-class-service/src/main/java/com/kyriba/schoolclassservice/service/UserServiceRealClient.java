package com.kyriba.schoolclassservice.service;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.HeadTeacherExternalDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.PupilExternalDto;
import feign.FeignException;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Profile("container")
public class UserServiceRealClient implements UserServiceClient
{
  private final UserServiceFeignClient client;

  public UserServiceRealClient(UserServiceFeignClient client)
  {
    this.client = client;
  }


  @Override
  public Optional<PupilDto> findById(PupilDto pupil)
  {
    return getIfPresent(() -> client.getPupil(pupil.getId()).toInternal());
  }


  @Override
  public Optional<HeadTeacherDto> findById(HeadTeacherDto teacher)
  {
    return getIfPresent(() -> client.getTeacher(teacher.getId()).toInternal());
  }


  private <T> Optional<T> getIfPresent(Supplier<T> supplier)
  {
    try {
      return Optional.ofNullable(supplier.get());
    } catch (FeignException e) {
      if (e.status() == 404) {
        return Optional.empty();
      } else {
        throw e;
      }
    }
  }


  @FeignClient(name = "user-service", url = "${kyriba.userservice.url}")
  public interface UserServiceFeignClient
  {
    @RequestMapping(method = RequestMethod.GET, value = "/pupils/{id}")
    PupilExternalDto getPupil(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value = "/teachers/{id}")
    HeadTeacherExternalDto getTeacher(@PathVariable("id") long id);
  }

}
