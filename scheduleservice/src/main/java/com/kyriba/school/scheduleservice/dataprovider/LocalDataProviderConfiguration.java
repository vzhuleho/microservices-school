package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.dao.PupilRepository;
import com.kyriba.school.scheduleservice.dao.SchoolClassRepository;
import com.kyriba.school.scheduleservice.dao.SubjectRepository;
import com.kyriba.school.scheduleservice.dao.TeacherRepository;
import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;
import com.kyriba.school.scheduleservice.domain.dto.SubjectDetails;
import com.kyriba.school.scheduleservice.domain.dto.TeacherDetails;
import com.kyriba.school.scheduleservice.domain.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Configuration
@RequiredArgsConstructor
@Profile("!container")
public class LocalDataProviderConfiguration {


	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;
	private final SchoolClassRepository schoolClassRepository;
	private final PupilRepository pupilRepository;
	private final ModelMapper mapper;

	@Bean
	public UserDataProvider userDataProvider() {
		return new LocalUserDataProvider();
	}

	@Bean
	public CurriculumDataProvider curriculumDataProvider() {
		return new LocalCurriculumDataProvider();
	}

	@Bean
	public SchoolClassDataProvider schoolClassDataProvider() {
		return new LocalSchoolClassDataProvider();
	}

	private class LocalUserDataProvider implements UserDataProvider {

		@Override
		public TeacherDetails getTeacher(long id) {
			return teacherRepository.findById(id).map(teacher -> mapper.map(teacher, TeacherDetails.class))
					.orElseThrow(ResourceNotFoundException::new);
		}
	}

	private class LocalCurriculumDataProvider implements CurriculumDataProvider {

		@Override
		public SubjectDetails getSubject(long id) {
			Subject subject = subjectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
			return mapper.map(subject, SubjectDetails.class);
		}

		@Override
		public List<SubjectDetails> getSubjects() {
			return StreamSupport.stream(subjectRepository.findAll().spliterator(), true)
					.map(subject -> mapper.map(subject, SubjectDetails.class))
					.collect(Collectors.toList());
		}
	}

	private class LocalSchoolClassDataProvider implements SchoolClassDataProvider {

		@Override
		public List<SchoolClassDetails> getSchoolClasses() {
			return StreamSupport.stream(schoolClassRepository.findAll().spliterator(), true)
					.map(schoolClass -> mapper.map(schoolClass, SchoolClassDetails.class))
					.collect(Collectors.toList());
		}

		@Override
		public SchoolClassDetails getSchoolClass(long id) {
			return mapper.map(schoolClassRepository.findById(id), SchoolClassDetails.class);
		}

		@Override
		public List<PupilDetails> getPupilsBySchoolClass(long id) {
			return pupilRepository.findBySchoolClassId(id).parallelStream()
					.map(pupil -> mapper.map(pupil, PupilDetails.class))
					.collect(Collectors.toList());
		}
	}

}
