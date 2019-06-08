package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.User;

public class Teacher extends User<TeacherEntity> {

  public Teacher(TeacherEntity root) {
    super(root);
  }

  public TeacherOutputModel toOutputModel() {
    var id = root.getId();
    var info = toOutputModel(root.getInfo());
    var address = toOutputModel(root.getAddress());
    var teacherInfo = root.getTeacherInfo();
    return new TeacherOutputModel(id, info, address, teacherInfo);
  }

  public void update(TeacherInputModel teacherModel) {
    super.update(teacherModel);
    root.updateInfo(teacherModel.getTeacherInfo());
  }

}
