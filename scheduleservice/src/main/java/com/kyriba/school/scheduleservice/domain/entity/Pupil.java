package com.kyriba.school.scheduleservice.domain.entity;

import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Pupil {

    @Id
    @Column
    @NonNull
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


    PupilDetails output() {
        return new PupilDetails(id, name);
    }
}
