package com.kyriba.schoolclassservice.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Vitaly Belkevich
 */
@Entity
@Table(name = "HEAD_TEACHER")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HeadTeacherEntity
{
  @Id
  @Column(name = "TEACHER_ID")
  Long id;

  @Column(name = "FULL_NAME")
  String fullname;
}

