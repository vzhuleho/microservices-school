package by.amushinsky.school.userservice.user.parent;

import by.amushinsky.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parents")
public class ParentController extends UserController<ParentDto, ParentService> {

  public ParentController(ParentService parentService) {
    super(parentService);
  }
}
