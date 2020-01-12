package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.TeacherDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@EnableFeignClients(clients = {ExternalUserDataProvider.UserServiceClient.class})
@Profile("container")
@Repository
class ExternalUserDataProvider implements UserDataProvider {

    private final UserServiceClient userServiceClient;

    @Override
    public TeacherDetails getTeacher(long id) {
        return userServiceClient.getTeacher(id);
    }

    @FeignClient(name = "user-service", url = "${kyriba.userservice.url:}")
    interface UserServiceClient extends UserDataProvider {

        @Override
        @RequestMapping(method = RequestMethod.GET, value = "/teachers/{id}")
        TeacherDetails getTeacher(@PathVariable long id);
    }

}
