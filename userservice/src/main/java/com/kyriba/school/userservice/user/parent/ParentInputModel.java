package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserInputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;

public class ParentInputModel extends UserInputModel {

  public ParentInputModel(UserInfo userInfo, AddressInfo addressInfo) {
    super(userInfo, addressInfo);
  }
}
