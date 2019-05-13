package by.amushinsky.school.userservice.user;

import lombok.Data;

@Data
public abstract class UserDto {

  private long id;

  private String name;

  private Status status;

  private Address address;

}
