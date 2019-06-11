package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserInputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;

public class PrincipalInputModel extends UserInputModel {

  public PrincipalInputModel(UserInfo userInfo, AddressInfo addressInfo) {
    super(userInfo, addressInfo);
  }

}
