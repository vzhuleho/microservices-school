package com.kyriba.school.scheduleservice.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Pupil {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    @NonNull
    private String name;

    @OneToMany(mappedBy = "pupil")
    @Getter
    private List<Mark> marks;

    @OneToMany(mappedBy = "pupil")
    @Getter
    private List<Absence> absences;

    @ManyToOne
    @Getter
    @NonNull
    private SchoolClass schoolClass;
}
