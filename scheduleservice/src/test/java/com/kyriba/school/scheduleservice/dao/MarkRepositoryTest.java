package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Lesson;
import com.kyriba.school.scheduleservice.domain.entity.Mark;
import com.kyriba.school.scheduleservice.domain.entity.Pupil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@DBRider
@ActiveProfiles("test")
public class MarkRepositoryTest {

    @Autowired
    protected MarkRepository markRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DataSet("test-dataset.xml")
    @ExpectedDataSet("expected-marks.xml")
    public void marksMayBeStored() {
        markRepository.save(new Mark(9, "note", new Pupil().setId(1L), new Lesson().setId(1L)));
        entityManager.flush();
    }

}