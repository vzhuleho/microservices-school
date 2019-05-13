package by.amushinsky.school.userservice.user.principal;

import by.amushinsky.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/principals")
public class PrincipalController extends UserController<PrincipalDto, PrincipalService> {

  public PrincipalController(PrincipalService principalService) {
    super(principalService);
  }
}
