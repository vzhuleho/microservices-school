package com.kyriba.schoolclassservice.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Vitaly Belkevich
 */
@Entity
@Table(name = "HEAD_TEACHER")
@Getter @Setter @NoArgsConstructor
@Builder @AllArgsConstructor
public class HeadTeacherEntity {
  @GeneratedValue
  @Id
  @Column(name = "TEACHER_ID")
  Long id;

  @Column(name = "FULL_NAME")
  String fullname;
}

