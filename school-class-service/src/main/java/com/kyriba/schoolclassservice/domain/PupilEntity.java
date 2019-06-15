package com.kyriba.schoolclassservice.domain;

import lombok.*;

import javax.persistence.*;


/**
 * @author Vitaly Belkevich
 */
@Entity
@Table(name = "PUPIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PupilEntity
{
  @Id
  @Column(name = "PUPIL_ID")
  Long id;

  @Column(name = "FULL_NAME")
  String fullname;

  @ManyToOne(targetEntity = SchoolClassEntity.class)
  @JoinColumn(name = "CLASS_ID")
  SchoolClassEntity schoolClass;
}
