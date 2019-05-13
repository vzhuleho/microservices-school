package by.amushinsky.school.userservice.user.teacher;

import by.amushinsky.school.userservice.user.UserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController extends UserController<TeacherDto, TeacherService> {

  public TeacherController(TeacherService teacherService) {
    super(teacherService);
  }
}
