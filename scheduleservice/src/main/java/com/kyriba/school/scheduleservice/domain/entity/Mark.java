package com.kyriba.school.scheduleservice.domain.entity;

import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
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

    public Mark(MarkDTO dto) {
        this(dto.getValue(), dto.getNote());
    }

    public Mark applyData(MarkDTO dto) {
        value(dto.getValue());
        note(dto.getNote());
        return this;
    }

    public MarkDTO output() {
        return new MarkDTO(pupil.name(), value, note);
    }
}
