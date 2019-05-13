package by.amushinsky.school.userservice.user.pupil;

import by.amushinsky.school.userservice.user.UserDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PupilDto extends UserDto {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  private int grade;

}
