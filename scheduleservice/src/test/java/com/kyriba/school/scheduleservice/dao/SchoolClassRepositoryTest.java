package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
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
public class SchoolClassRepositoryTest {

    @Autowired
    protected SchoolClassRepository schoolClassRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DataSet("test-dataset.xml")
    @ExpectedDataSet("expected-school-classes.xml")
    public void schoolClassesMayBeStored() {
        SchoolClass schoolClass = new SchoolClass().setId(3L).setYear(2019).setGrade(1).setLetter("C");
        schoolClassRepository.save(schoolClass);
        entityManager.flush();
    }

}