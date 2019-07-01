package com.kyriba.schoolclassservice.domain;

import com.kyriba.schoolclassservice.service.dto.PupilDto;
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

  @ManyToOne
  @JoinColumn(name = "CLASS_ID")
  SchoolClassEntity schoolClass;


  public PupilDto toDto()
  {
    final PupilDto pupilDto = new PupilDto();
    pupilDto.setId(getId());
    pupilDto.setName(getFullname());
    return pupilDto;
  }


  public PupilEntity populateFrom(PupilDto dto)
  {
    if (getId() == null) {
      setId(dto.getId());
    }
    setFullname(dto.getName());
    return this;
  }
}
