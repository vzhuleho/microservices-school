package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@EnableFeignClients(clients = {ExternalSchoolClassDataProvider.SchoolServiceClient.class})
@Profile("container")
@Repository
class ExternalSchoolClassDataProvider implements SchoolClassDataProvider {

    private final SchoolServiceClient schoolServiceClient;

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

    @FeignClient(name = "school-class-service", url = "${kyriba.schoolclassservice.url:}")
    interface SchoolServiceClient {

        @GetMapping("/classes")
        List<SchoolClassDetails> getSchoolClasses();

        @GetMapping("/classes/{id}")
        SchoolClassDetails getSchoolClass(@PathVariable long id);

        @GetMapping("/classes/{id}/pupils")
        List<PupilDetails> getPupilsByClass(@PathVariable long id);

    }
}
