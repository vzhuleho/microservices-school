package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pupils")
public class PupilController
    extends UserController<PupilOutputModel, PupilInputModel, PupilService> {

  public PupilController(PupilService pupilService) {
    super(pupilService);
  }

}
