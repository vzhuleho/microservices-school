package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserOutputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParentOutputModel extends UserOutputModel {

  public ParentOutputModel(long id, UserInfo userInfo, AddressInfo addressInfo) {
    super(id, userInfo, addressInfo);
  }

}
