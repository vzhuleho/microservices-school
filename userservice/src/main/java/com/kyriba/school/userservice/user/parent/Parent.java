package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.User;

public class Parent extends User<ParentEntity> {

  public Parent(ParentEntity root) {
    super(root);
  }

  public ParentOutputModel toOutputModel() {
    var id = root.getId();
    var info = toOutputModel(root.getInfo());
    var address = toOutputModel(root.getAddress());
    return new ParentOutputModel(id, info, address);
  }

  public void update(ParentInputModel parentModel) {
    super.update(parentModel);
  }
}
