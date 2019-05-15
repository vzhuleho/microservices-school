package com.kyriba.school.scheduleservice.domain.schoolclass;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

class SchoolClassNotFoundException extends ResourceNotFoundException {

    SchoolClassNotFoundException(int grade, String letter, int year) {
        super(createMessage(grade, letter, year));
    }

    private static String createMessage(int grade, String letter, int year) {
        return String.format("School class [%s%s of %s] was not found", grade, letter.toUpperCase(), year);
    }

}
