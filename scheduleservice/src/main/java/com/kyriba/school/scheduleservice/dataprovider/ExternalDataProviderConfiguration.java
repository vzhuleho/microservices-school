package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;
import com.kyriba.school.scheduleservice.domain.dto.SubjectDetails;
import com.kyriba.school.scheduleservice.domain.dto.TeacherDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@EnableFeignClients
@Profile("container")
public class ExternalDataProviderConfiguration {

	private final UserServiceClient userServiceClient;
	private final CurriculumServiceClient curriculumServiceClient;
	private final SchoolServiceClient schoolServiceClient;

	@Bean
	public UserDataProvider userDataProvider() {
		return userServiceClient;
	}

	@Bean
	public CurriculumDataProvider curriculumDataProvider() {
		return new ExternalCurriculumDataProvider();
	}

	@Bean
	public SchoolClassDataProvider schoolClassDataProvider() {
		return new ExternalSchoolClassDataProvider();
	}

	@FeignClient(name = "user-service", url = "${kyriba.userservice.url:}")
	private interface UserServiceClient extends UserDataProvider {

		@Override
		@RequestMapping(method = RequestMethod.GET, value = "/teachers/{id}")
		TeacherDetails getTeacher(@PathVariable long id);
	}

	@RequiredArgsConstructor
	private class ExternalCurriculumDataProvider implements CurriculumDataProvider {

		@Override
		public SubjectDetails getSubject(long id) {
			return getSubjects().stream().filter(subjectDetails -> subjectDetails.getId().equals(id)).findAny()
					.orElseThrow(ResourceNotFoundException::new);
		}

		@Override
		public List<SubjectDetails> getSubjects() {
			return curriculumServiceClient.getSubjects();
		}
	}

	@FeignClient(name = "curriculum-service", url = "${kyriba.curriculumservice.url:}")
	private interface CurriculumServiceClient {

		@RequestMapping(method = RequestMethod.GET, value = "/subjects")
		List<SubjectDetails> getSubjects();

	}

	@RequiredArgsConstructor
	private class ExternalSchoolClassDataProvider implements SchoolClassDataProvider {

		@Override
		public List<SchoolClassDetails> getSchoolClasses() {
			return schoolServiceClient.getSchoolClasses();
		}

		@Override
		public SchoolClassDetails getSchoolClass(long id) {
			SchoolClassDetails schoolClass = schoolServiceClient.getSchoolClass(id);
			schoolClass.setPupils(schoolServiceClient.getPupilsByClass(id));
			return schoolClass;
		}

		@Override
		public List<PupilDetails> getPupilsBySchoolClass(long id) {
			return schoolServiceClient.getPupilsByClass(id);
		}
	}

	@FeignClient(name = "school-class-service", url = "${kyriba.schoolclassservice.url:}")
	private interface SchoolServiceClient {

		@GetMapping("/classes")
		List<SchoolClassDetails> getSchoolClasses();

		@GetMapping("/classes/{id}")
		SchoolClassDetails getSchoolClass(@PathVariable long id);

		@GetMapping("/classes/{id}/pupils")
		List<PupilDetails> getPupilsByClass(@PathVariable long id);

	}
}
