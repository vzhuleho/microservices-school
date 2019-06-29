package com.kyriba.schoolclassservice.domain;

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
    @Setter(AccessLevel.PROTECTED)
    @Column(name = "SCHOOL_CLASS_ID", nullable = false)
    Long id;

    @Column(name = "GRADE", nullable = false)
    Integer grade;
    @Column(name = "LETTER", nullable = false)
    String letter;
    @Column(name = "YEAR", nullable = false)
    int year;


    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY)
    Set<PupilEntity> pupils = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    HeadTeacherEntity headTeacherEntity;
}
