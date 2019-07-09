package com.kyriba.school.scheduleservice.domain.entity;

import com.kyriba.school.scheduleservice.domain.dto.AbsenceDetails;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors
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
        setLesson(lesson);
    }

    public Absence(String reason, Pupil pupil) {
        this(reason);
        setPupil(pupil);
    }

    public Absence(String reason) {
        setReason(reason);
    }

    public Absence applyData(AbsenceRequest absenceRequest) {
        setReason(absenceRequest.getReason());
        return this;
    }

    public AbsenceDetails output() {
        return new AbsenceDetails(id, pupil.output(), reason, lesson.getId());
    }
}
