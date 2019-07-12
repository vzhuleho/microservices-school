package com.kyriba.school.userservice.user;

import com.kyriba.school.userservice.UserServiceError;
import com.kyriba.school.userservice.UserServiceError.Code;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public abstract class UserController
    <O extends UserOutputModel, I extends UserInputModel, S extends UserService<O, I>> {

  protected final S service;

  protected UserController(S service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public O get(@PathVariable long id) throws NoSuchUserException {
    return service.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public O add(@RequestBody I userModel) {
    return service.create(userModel);
  }

  @PutMapping(value = "/{id}")
  public O update(@PathVariable long id, @RequestBody I userModel) throws NoSuchUserException {
    return service.update(id, userModel);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchUserException.class)
  public UserServiceError handleUserNotFound(NoSuchUserException ex) {
    log.warn(ex.getMessage());
    return new UserServiceError(Code.USER_NOT_FOUND, ex.getMessage());
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public UserServiceError handleUserNotValid(ValidationException ex) {
    log.warn(ex.getMessage());
    return new UserServiceError(Code.USER_NOT_VALID, ex.getMessage());
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public UserServiceError handleUnknownError(Exception ex) {
    log.error(ex.getMessage(), ex);
    return new UserServiceError(Code.UNKNOWN, "Internal error");
  }

}
