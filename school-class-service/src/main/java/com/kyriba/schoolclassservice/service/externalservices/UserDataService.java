/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.service.externalservices;

import com.kyriba.schoolclassservice.domain.HeadTeacherEntity;
import com.kyriba.schoolclassservice.domain.PupilEntity;
import com.kyriba.schoolclassservice.repository.PupilRepository;
import com.kyriba.schoolclassservice.repository.TeacherRepository;
import com.kyriba.schoolclassservice.service.dto.HeadTeacherDto;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Optional;


/**
 * @author M-VBE
 * @since 19.2
 */
@Component
@RequiredArgsConstructor
public class UserDataService {
    private final UserServiceClient userServiceClient;

    private final TeacherRepository teacherRepository;
    private final PupilRepository pupilRepository;


    public Optional<PupilEntity> findById(PupilDto pupul) {
        return Optional.ofNullable(fetchPupil(pupul));
    }


    private PupilEntity fetchPupil(@Valid final PupilDto pupilDto) {
        final Long id = pupilDto.getId();
        final Optional<PupilEntity> localStoragePupil = pupilRepository.findById(id);
        return localStoragePupil
                .orElseGet(() -> userServiceClient.findById(pupilDto)
                        .map((pupil) -> new PupilEntity().populateFrom(pupil))
                        .map(pupilRepository::save)
                        .orElse(null));
    }


    public Optional<HeadTeacherEntity> findById(HeadTeacherDto headTeacherDto) {
        return Optional.ofNullable(fetchTeacher(headTeacherDto));
    }


    private HeadTeacherEntity fetchTeacher(HeadTeacherDto headTeacherDto) {
        Optional<HeadTeacherEntity> locallyStoredTeacher = teacherRepository.findById(headTeacherDto.getId());

        return locallyStoredTeacher.orElseGet(() ->
                userServiceClient.findById(headTeacherDto)
                        .map(HeadTeacherEntity::fromDto)
                        .map(teacherRepository::save)
                        .orElse(null));
    }

}
