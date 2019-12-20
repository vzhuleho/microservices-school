package com.kyriba.school.scheduleservice.dao;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kyriba.school.scheduleservice.domain.entity.Teacher;
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
public class TeacherRepositoryTest {

    @Autowired
    protected TeacherRepository teacherRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @ExpectedDataSet("expected-teachers.xml")
    public void teachersMayBeStored() {
        Teacher teacher = new Teacher().setId(1L).setName("Petr Petrov").setStatus(Teacher.Status.ACTIVE);
        teacherRepository.save(teacher);
        entityManager.flush();
    }

}