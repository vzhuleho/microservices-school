package com.kyriba.school.userservice.user.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {

  private String city = "N/A";

  private String street = "N/A";

  private String house = "N/A";

  private String apartment = "N/A";

}
