package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.SubjectDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequiredArgsConstructor
@EnableFeignClients
@Profile("container")
@Repository
class ExternalCurriculumDataProvider implements CurriculumDataProvider {

    private final CurriculumServiceClient curriculumServiceClient;

    @Override
    public SubjectDetails getSubject(long id) {
        return getSubjects().stream().filter(subjectDetails -> subjectDetails.getId().equals(id)).findAny()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<SubjectDetails> getSubjects() {
        return curriculumServiceClient.getSubjects();
    }

    @FeignClient(name = "curriculum-service", url = "${kyriba.curriculumservice.url:}")
    private interface CurriculumServiceClient {

        @RequestMapping(method = RequestMethod.GET, value = "/subjects")
        List<SubjectDetails> getSubjects();

    }
}
