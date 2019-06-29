package com.kyriba.schoolclassservice.service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadTeacherExternalDto
{
  private long id;

  private UserInfo userInfo;


  public HeadTeacherDto toInternal()
  {
    return new HeadTeacherDto(id, userInfo.getName());
  }

}
