package com.kyriba.school.userservice.user;

import java.util.NoSuchElementException;
import lombok.Getter;

public class NoSuchUserException extends NoSuchElementException {

  private static final long serialVersionUID = 6769889250639411880L;

  @Getter
  private final long userId;

  public NoSuchUserException(long userId) {
    this.userId = userId;
  }

  public NoSuchUserException(String s, long userId) {
    super(s);
    this.userId = userId;
  }

  @Override
  public String getMessage() {
    var message = super.getMessage();
    return message == null ? String.format("User with id %d doesn't exist", userId) : message;
  }
}
