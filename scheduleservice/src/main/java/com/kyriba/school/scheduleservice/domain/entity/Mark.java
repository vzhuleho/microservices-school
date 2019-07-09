package com.kyriba.school.scheduleservice.domain.entity;

import com.kyriba.school.scheduleservice.domain.dto.MarkDetails;
import com.kyriba.school.scheduleservice.domain.dto.MarkRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Mark {

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
    private int value;

    @Column
    private String note;

    public Mark(int value, String note, Pupil pupil, Lesson lesson) {
        this(value, note, pupil);
        lesson(lesson);
    }

    public Mark(int value, String note, Pupil pupil) {
        this(value, note);
        pupil(pupil);
    }

    public Mark(int value, String note) {
        value(value);
        note(note);
    }

    public Mark(MarkRequest markRequest) {
        this(markRequest.getValue(), markRequest.getNote());
    }

    public Mark applyData(MarkRequest markRequest) {
        value(markRequest.getValue());
        note(markRequest.getNote());
        return this;
    }

    public MarkDetails output() {
        return new MarkDetails(id, pupil.output(), value, note, lesson.getId());
    }
}
