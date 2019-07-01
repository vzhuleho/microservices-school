package com.kyriba.schoolclassservice.domain;

import com.kyriba.schoolclassservice.service.dto.ClassUpdateRequest;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vitaly Belkevich
 */
@Entity
@Table(name = "SCHOOL_CLASS")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHOOL_CLASS_ID", nullable = false)
    Long id;

    @Column(name = "GRADE", nullable = false)
    Integer grade;
    @Column(name = "LETTER", nullable = false)
    String letter;
    @Column(name = "YEAR", nullable = false)
    int year;


    @OneToMany(mappedBy = "schoolClass")
    Set<PupilEntity> pupils = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "TEACHER_ID")
    HeadTeacherEntity headTeacherEntity;


  public SchoolClassEntity populateFrom(ClassUpdateRequest classUpdateRequest)
  {
    setGrade(classUpdateRequest.getGrade());
    setLetter(classUpdateRequest.getLetter());
    return this;
  }


  public SchoolClassDto toDto()
  {
    return SchoolClassDto.builder()
        .id(this.getId())
        .grade(this.getGrade())
        .letter(this.getLetter())
        .year(this.getYear())
        .headTeacher(this.getHeadTeacherEntity() == null ? null : this.getHeadTeacherEntity().toDto())
        .build();
  }


  public SchoolClassEntity populateFrom(SchoolClassDto dto)
  {
    if (getId() == null) {
      setId(dto.getId());
    }
    setGrade(dto.getGrade());
    setLetter(dto.getLetter());
    setYear(dto.getYear());
    return this;
  }
}
