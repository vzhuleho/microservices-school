package com.kyriba.school.scheduleservice.configuration;

import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassRepository;
import com.kyriba.school.scheduleservice.domain.subject.Subject;
import com.kyriba.school.scheduleservice.domain.subject.SubjectRepository;
import com.kyriba.school.scheduleservice.domain.teacher.Teacher;
import com.kyriba.school.scheduleservice.domain.teacher.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;


@Configuration
public class ApplicationConfiguration {

	private final SchoolClassRepository schoolClassRepository;
	private final SubjectRepository subjectRepository;
	private final TeacherRepository teacherRepository;


	public ApplicationConfiguration(SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository,
	                                TeacherRepository teacherRepository) {
		this.schoolClassRepository = schoolClassRepository;
		this.subjectRepository = subjectRepository;
		this.teacherRepository = teacherRepository;
	}


	@Bean
	@Profile("dev")
	CommandLineRunner devDataLoader() {
		return (args) -> {
			schoolClassRepository.save(new SchoolClass(1, "A", 2019));
			schoolClassRepository.save(new SchoolClass(1, "A", 2018));
			schoolClassRepository.save(new SchoolClass(1, "B", 2019));
			subjectRepository.save(new Subject("Biology"));
			subjectRepository.save(new Subject("Russian Literature"));
			teacherRepository.save(new Teacher("Ivan Ivanov"));
		};
	}

	@Bean public SpelAwareProxyProjectionFactory projectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}


}
