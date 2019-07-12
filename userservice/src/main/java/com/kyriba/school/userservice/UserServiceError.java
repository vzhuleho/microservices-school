package com.kyriba.school.userservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserServiceError {

  private Code code;

  private String message;

  public enum Code {
    USER_NOT_FOUND,
    USER_NOT_VALID,
    UNKNOWN
  }
}
