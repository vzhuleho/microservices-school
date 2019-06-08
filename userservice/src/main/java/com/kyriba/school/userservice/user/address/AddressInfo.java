package com.kyriba.school.userservice.user.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {

  private LocationInfo locationInfo = new LocationInfo();

  private PostalInfo postalInfo = new PostalInfo();

  private CommunicationInfo communicationInfo = new CommunicationInfo();

}
