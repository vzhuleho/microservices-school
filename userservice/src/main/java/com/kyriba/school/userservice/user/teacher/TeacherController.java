package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController
    extends UserController<TeacherOutputModel, TeacherInputModel, TeacherService> {

  public TeacherController(TeacherService teacherService) {
    super(teacherService);
  }
}
