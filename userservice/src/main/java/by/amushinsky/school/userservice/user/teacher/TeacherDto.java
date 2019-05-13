package by.amushinsky.school.userservice.user.teacher;

import by.amushinsky.school.userservice.user.Money;
import by.amushinsky.school.userservice.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends UserDto {

  private String passportNumber;

  private Money salary;

  private Money bonus;

}
