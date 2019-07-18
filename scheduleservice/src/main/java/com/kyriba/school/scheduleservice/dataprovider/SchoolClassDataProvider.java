package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.PupilDetails;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDetails;

import java.util.List;

public interface SchoolClassDataProvider {

    List<SchoolClassDetails> getSchoolClasses();

    SchoolClassDetails getSchoolClass(long id);

    List<PupilDetails> getPupilsBySchoolClass(long id);

}
