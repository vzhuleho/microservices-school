package com.kyriba.school.userservice.user;

public interface UserService<O extends UserOutputModel, I extends UserInputModel> {

  O findById(long id) throws NoSuchUserException;

  O create(I userModel);

  O update(long id, I userModel) throws NoSuchUserException;

}
