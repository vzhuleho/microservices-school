package com.kyriba.school.scheduleservice.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ScheduleNotFoundException extends ResourceNotFoundException {

    ScheduleNotFoundException(long id) {
        super(String.format("Schedule with id [%s] was not found", id));
    }

    ScheduleNotFoundException(int grade, String letter, int year) {
        super(createMessage(grade, letter, year));
    }

    private static String createMessage(int grade, String letter, int year) {
        return String.format("A schedule for the school class [%s%s] for year [%s] was not found", grade, letter.toUpperCase(), year);
    }
}
