package com.kyriba.schoolclassservice.domain;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@AllArgsConstructor
public class HeadTeacherEntity
{
  @Id
  @Column(name = "TEACHER_ID")
  Long id;

  @Column(name = "FULL_NAME")
  String fullname;


  public static HeadTeacherEntity fromDto(HeadTeacherDto dto)
  {
    final HeadTeacherEntity headTeacherEntity = new HeadTeacherEntity();
    headTeacherEntity.setId(dto.getId());
    headTeacherEntity.setFullname(dto.getName());
    return headTeacherEntity;
  }


  public HeadTeacherDto toDto()
  {
    final HeadTeacherDto headTeacherDto = new HeadTeacherDto();
    headTeacherDto.setId(getId());
    headTeacherDto.setName(getFullname());
    return headTeacherDto;
  }
}

