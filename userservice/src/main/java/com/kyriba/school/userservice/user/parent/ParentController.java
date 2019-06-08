package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parents")
public class ParentController
    extends UserController<ParentOutputModel, ParentInputModel, ParentService> {

  public ParentController(ParentService parentService) {
    super(parentService);
  }

}
