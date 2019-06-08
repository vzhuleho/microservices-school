package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/principals")
public class PrincipalController
    extends UserController<PrincipalOutputModel, PrincipalInputModel, PrincipalService> {

  public PrincipalController(PrincipalService principalService) {
    super(principalService);
  }

}
