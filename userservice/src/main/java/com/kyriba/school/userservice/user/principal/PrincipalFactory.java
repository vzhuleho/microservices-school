package com.kyriba.school.userservice.user.principal;

import org.springframework.stereotype.Service;

@Service
public class PrincipalFactory {

  public Principal create(PrincipalEntity entity) {
    return new Principal(entity);
  }

  public Principal create(PrincipalInputModel principalModel) {
    Principal principal = create(new PrincipalEntity());
    principal.update(principalModel);
    return principal;
  }

}
