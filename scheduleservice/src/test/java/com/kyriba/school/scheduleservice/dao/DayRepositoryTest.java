package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
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
public class DayRepositoryTest {

    @Autowired
    protected DayRepository dayRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DataSet("test-dataset.xml")
    @ExpectedDataSet("expected-days.xml")
    public void daysMayBeStored() {
        Day day = new Day(LocalDate.of(2019, Month.JANUARY, 1), new Schedule().setId(1L));
        dayRepository.save(day);
        entityManager.flush();
    }

}