package com.kyriba.school.scheduleservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Pupil {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @OneToMany
    @JoinColumn
    @Getter
    private List<Mark> marks;

    @OneToMany
    @JoinColumn
    @Getter
    private List<Absence> absences;

    @ManyToOne
    @Getter
    private SchoolClass schoolClass;
}
