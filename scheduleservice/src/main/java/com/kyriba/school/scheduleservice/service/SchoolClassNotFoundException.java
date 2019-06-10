package com.kyriba.school.scheduleservice.service;

import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

class SchoolClassNotFoundException extends ResourceNotFoundException {

    SchoolClassNotFoundException(int grade, String letter, int year) {
        super(createMessage(grade, letter, year));
    }

    SchoolClassNotFoundException(SchoolClass schoolClassToFind) {
        super(createMessage(schoolClassToFind.getGrade(), schoolClassToFind.getLetter(), schoolClassToFind.getFoundationYear()));
    }

    private static String createMessage(int grade, String letter, int year) {
        return String.format("School class [%s%s of %s] was not found", grade, letter.toUpperCase(), year);
    }
}
