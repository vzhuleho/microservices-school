package com.kyriba.school.userservice.user.pupil;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PupilInfo {

  private LocalDate birthDate = LocalDate.now();

  private int grade = 1;

}
