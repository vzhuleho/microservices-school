package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.UserEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@DiscriminatorValue("tch")
@Table(name = "teacher")
@Getter
public class TeacherEntity extends UserEntity {

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "passportNumber", column = @Column(name = "passport_number")),
      @AttributeOverride(name = "salary", column = @Column(name = "salary")),
      @AttributeOverride(name = "bonus", column = @Column(name = "bonus")),
  })
  private TeacherInfo teacherInfo = new TeacherInfo();

  public void updateInfo(TeacherInfo teacherInfo) {
    this.teacherInfo = teacherInfo;
  }

}
