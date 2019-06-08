package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserOutputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeacherOutputModel extends UserOutputModel {

  private TeacherInfo teacherInfo = new TeacherInfo();

  public TeacherOutputModel(
      long id, UserInfo userInfo, AddressInfo addressInfo, TeacherInfo teacherInfo) {
    super(id, userInfo, addressInfo);
    this.teacherInfo = teacherInfo;
  }

}
