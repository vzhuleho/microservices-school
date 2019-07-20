package com.kyriba.school.userservice.user;

import com.kyriba.school.userservice.user.address.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputModel {

  private long id = 0L;

  private UserInfo userInfo = new UserInfo();

  private AddressInfo addressInfo = new AddressInfo();

}
