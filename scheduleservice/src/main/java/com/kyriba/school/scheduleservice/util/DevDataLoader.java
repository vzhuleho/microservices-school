package com.kyriba.school.scheduleservice.util;

import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test"})
public class DevDataLoader implements CommandLineRunner {

	private final ScheduleRepository scheduleRepository;
	private final SchoolClassRepository schoolClassRepository;

	public DevDataLoader(ScheduleRepository scheduleRepository, SchoolClassRepository schoolClassRepository) {
		this.scheduleRepository = scheduleRepository;
		this.schoolClassRepository = schoolClassRepository;
	}

	@Override
	public void run(String... args) {
		SchoolClass schoolClass1 = new SchoolClass().setGrade(1).setLetter("A").setFoundationYear(2018);
		schoolClassRepository.save(schoolClass1);
		SchoolClass schoolClass2 = new SchoolClass().setGrade(1).setLetter("A").setFoundationYear(2019);
		schoolClassRepository.save(schoolClass2);
		SchoolClass schoolClass3 = new SchoolClass().setGrade(10).setLetter("Z").setFoundationYear(2009);
		SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass3);
		scheduleRepository.save(new Schedule(2019, savedSchoolClass));
	}
}
