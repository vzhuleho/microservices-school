package com.kyriba.schoolclassservice.domain;

import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Vitaly Belkevich
 */
@Entity(name = "SCHOOL_CLASS")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassEntity {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    @Column(name = "SCHOOL_CLASS_ID", nullable = false)
    Long id;

    @Column(name = "GRADE", nullable = false)
    Integer grade;
    @Column(name = "LETTER", nullable = false)
    String letter;
    @Column(name = "YEAR", nullable = false)
    int year;

    //todo: head teacher
    //todo: pupils
}
