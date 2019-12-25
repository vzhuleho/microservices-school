package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;

@RunWith(SpringRunner.class)
@DataJpaTest
@DBRider
@ActiveProfiles("test")
public class LessonRepositoryTest {

    @Autowired
    protected LessonRepository lessonRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @ExpectedDataSet("expected-lessons.xml")
    public void lessonsMayBeStored() {
        Lesson lesson = new Lesson().setId(1L).setDate(LocalDate.of(2019, Month.JANUARY, 1)).setIndex(1).setNote("note");
        lessonRepository.save(lesson);
        entityManager.flush();
    }

}