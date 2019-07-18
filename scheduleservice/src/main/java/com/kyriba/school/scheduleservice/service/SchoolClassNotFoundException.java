package com.kyriba.school.scheduleservice.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class SchoolClassNotFoundException extends ResourceNotFoundException {

    SchoolClassNotFoundException(int grade, String letter, int year) {
        super(createMessage(grade, letter, year));
    }

	public SchoolClassNotFoundException(Long schoolClassId) {
		super(String.format("School class with id [%d] was not found", schoolClassId));
	}

	private static String createMessage(int grade, String letter, int year) {
        return String.format("School class [%s%s of %s] was not found", grade, letter.toUpperCase(), year);
    }
}
