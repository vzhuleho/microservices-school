package com.kyriba.school.scheduleservice.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class PupilNotFoundException extends ResourceNotFoundException {

	public PupilNotFoundException(Long pupilId) {
		super(String.format("A pupil with id [%d] was not found", pupilId));
	}
}
