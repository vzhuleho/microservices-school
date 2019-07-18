package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Absence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@DBRider
@ActiveProfiles("test")
@Commit
public class AbsenceRepositoryTest {

    @Autowired
    protected AbsenceRepository absenceRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @ExpectedDataSet("expected-absences.xml")
    public void absencesMayBeStored() {
        absenceRepository.save(new Absence( "reason"));
        entityManager.flush();
    }

}