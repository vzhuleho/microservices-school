package com.kyriba.school.userservice.user.teacher;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfo {

  private String passportNumber = "N/A";

  private BigDecimal salary = BigDecimal.ZERO;

  private BigDecimal bonus = BigDecimal.ZERO;

}
