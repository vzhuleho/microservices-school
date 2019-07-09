package com.kyriba.school.scheduleservice.dataprovider;

import com.kyriba.school.scheduleservice.domain.dto.SubjectDetails;

import java.util.List;

public interface CurriculumDataProvider {

	SubjectDetails getSubject(long id);

	List<SubjectDetails> getSubjects();

}
