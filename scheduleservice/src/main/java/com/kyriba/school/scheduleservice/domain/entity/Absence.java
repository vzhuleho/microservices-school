package com.kyriba.school.scheduleservice.domain.entity;

import com.kyriba.school.scheduleservice.domain.dto.AbsenceDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
public class Absence {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Pupil pupil;

    @ManyToOne
    @JoinColumn
    private Lesson lesson;

    @Column
    private String reason;

    public Absence(String reason, Pupil pupil, Lesson lesson) {
        this(reason, pupil);
        lesson(lesson);
    }

    public Absence(String reason, Pupil pupil) {
        this(reason);
        pupil(pupil);
    }

    public Absence(String reason) {
        reason(reason);
    }

    public Absence(AbsenceDTO dto) {
        this(dto.reason());
    }

    public Absence applyData(AbsenceDTO dto) {
        reason(dto.reason());
        return this;
    }
}
