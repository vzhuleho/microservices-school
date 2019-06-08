package com.kyriba.school.userservice.user.parent;

import org.springframework.stereotype.Service;

@Service
public class ParentFactory {

  public Parent create(ParentEntity entity) {
    return new Parent(entity);
  }

  public Parent create(ParentInputModel parentModel) {
    Parent parent = create(new ParentEntity());
    parent.update(parentModel);
    return parent;
  }
}
