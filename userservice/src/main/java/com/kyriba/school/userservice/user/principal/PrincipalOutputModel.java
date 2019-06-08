package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserOutputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PrincipalOutputModel extends UserOutputModel {

  public PrincipalOutputModel(long id, UserInfo userInfo, AddressInfo addressInfo) {
    super(id, userInfo, addressInfo);
  }

}
