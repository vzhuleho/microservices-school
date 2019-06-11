package com.kyriba.school.userservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  private String name = "";

  private Status status = Status.INACTIVE;

}
