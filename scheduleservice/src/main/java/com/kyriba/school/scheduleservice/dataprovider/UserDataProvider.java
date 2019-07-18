package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.TeacherDetails;

public interface UserDataProvider {

    TeacherDetails getTeacher(long id);

}
