/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 05.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices;


import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.externalservices.dto.HeadTeacherExternalDto;
import com.kyriba.schoolclassservice.service.externalservices.dto.PupilExternalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@Profile("container")
public class UserServiceRealClient implements UserServiceClient {
    private final UserServiceFeignClient client;

    @Autowired
    public UserServiceRealClient(UserServiceFeignClient client) {
        this.client = client;
    }


    @Override
    public Optional<PupilDto> findById(PupilDto pupil) {
        return client.getPupil(pupil.getId()).map(PupilExternalDto::toInternal);
    }


    @Override
    public Optional<HeadTeacherDto> findById(HeadTeacherDto teacher) {
        return client.getTeacher(teacher.getId()).map(HeadTeacherExternalDto::toInternal);
    }
    

    @FeignClient(name = "user-service", url = "${kyriba.userservice.url}", decode404 = true)
    public interface UserServiceFeignClient {
        @RequestMapping(method = RequestMethod.GET, value = "/pupils/{id}")
        Optional<PupilExternalDto> getPupil(@PathVariable("id") long id);

        @RequestMapping(method = RequestMethod.GET, value = "/teachers/{id}")
        Optional<HeadTeacherExternalDto> getTeacher(@PathVariable("id") long id);
    }
}
