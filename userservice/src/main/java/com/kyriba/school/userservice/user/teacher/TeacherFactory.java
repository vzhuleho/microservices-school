package com.kyriba.school.userservice.user.teacher;

import org.springframework.stereotype.Service;

@Service
public class TeacherFactory {

  public Teacher create(TeacherEntity entity) {
    return new Teacher(entity);
  }

  public Teacher create(TeacherInputModel parentModel) {
    Teacher teacher = create(new TeacherEntity());
    teacher.update(parentModel);
    return teacher;
  }

}
