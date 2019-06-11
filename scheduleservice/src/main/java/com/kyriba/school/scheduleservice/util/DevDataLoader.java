package com.kyriba.school.scheduleservice.util;

import com.kyriba.school.scheduleservice.dao.ScheduleRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test"})
@AllArgsConstructor
public class DevDataLoader implements CommandLineRunner {

	private final ScheduleRepository scheduleRepository;
	private final SchoolClassRepository schoolClassRepository;

	@Override
	public void run(String... args) {
		SchoolClass schoolClass1 = schoolClassRepository.save(new SchoolClass().setGrade(1).setLetter("A").setFoundationYear(2018));
		SchoolClass schoolClass2 = schoolClassRepository.save(new SchoolClass().setGrade(1).setLetter("A").setFoundationYear(2019));
		SchoolClass schoolClass3 = schoolClassRepository.save(new SchoolClass().setGrade(10).setLetter("Z").setFoundationYear(2009));
		scheduleRepository.save(new Schedule(2019, schoolClass3));
	}
}
