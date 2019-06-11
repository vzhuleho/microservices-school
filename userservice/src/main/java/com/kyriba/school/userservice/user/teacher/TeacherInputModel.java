package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserInputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeacherInputModel extends UserInputModel {

  private TeacherInfo teacherInfo;

  public TeacherInputModel(
      long id, UserInfo userInfo, AddressInfo addressInfo, TeacherInfo teacherInfo) {
    super(userInfo, addressInfo);
    this.teacherInfo = teacherInfo;
  }

}
