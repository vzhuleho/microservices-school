package com.kyriba.school.userservice.user;

import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInputModel {

  private UserInfo userInfo = new UserInfo();

  private AddressInfo addressInfo = new AddressInfo();

}
