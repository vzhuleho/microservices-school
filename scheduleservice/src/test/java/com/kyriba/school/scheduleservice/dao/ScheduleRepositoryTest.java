package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
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
public class ScheduleRepositoryTest {

    @Autowired
    protected ScheduleRepository scheduleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DataSet("test-dataset.xml")
    @ExpectedDataSet("expected-schedules.xml")
    public void schedulesMayBeStored() {
        Schedule schedule = new Schedule().setId(1L).setYear(2019).setSchoolClass(new SchoolClass().setId(2L));
        scheduleRepository.save(schedule);
        entityManager.flush();
    }

}