package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.User;

public class Principal extends User<PrincipalEntity> {

  public Principal(PrincipalEntity root) {
    super(root);
  }

  public PrincipalOutputModel toOutputModel() {
    var id = root.getId();
    var info = toOutputModel(root.getInfo());
    var address = toOutputModel(root.getAddress());
    return new PrincipalOutputModel(id, info, address);
  }

  public void update(PrincipalInputModel principalModel) {
    super.update(principalModel);
  }

}
