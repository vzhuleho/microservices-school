package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.ScheduleDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;


@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    @ApiOperation(value = "View a list of available schedules")
    @GetMapping
    public Page<ScheduleDTO> getAllSchedules(Pageable pageable) {
        return new PageImpl<>(Collections.singletonList(new ScheduleDTO()));
    }

    @ApiOperation(value = "Get a schedule by id", response = ScheduleDTO.class)
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ScheduleDTO getById(@PathVariable Long id) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setYear(2018);
        scheduleDTO.setSchoolClass(new SchoolClassDTO(1L, 1, "A", 2018));
        return scheduleDTO;
    }

    @ApiOperation(value = "Create a new schedule", response = ScheduleDTO.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/hal+json")
    public ScheduleDTO create(@RequestBody ScheduleDTO scheduleToCreate) {
        scheduleToCreate.setId(1L);
        return scheduleToCreate;
    }

    @ApiOperation(value = "Get a schedule or create a new one if the schedule for the requested year and school class doesn't exist",
            response = ScheduleDTO.class)
    @GetMapping(value = "/{year}/{grade}/{letter}", produces = "application/hal+json")
    public ScheduleDTO getOrCreate(@PathVariable int year,
                                   @PathVariable int grade,
                                   @PathVariable String letter) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setYear(year);
        scheduleDTO.setSchoolClass(new SchoolClassDTO(1L, 1, "A", 2018));
        return scheduleDTO;
    }

    @ApiOperation(value = "Update an existing schedule", response = ScheduleDTO.class)
    @PutMapping(produces = "application/hal+json")
    public ScheduleDTO updateSchedule(@Valid @RequestBody ScheduleDTO scheduleToUpdate) {
        return scheduleToUpdate;

    }

    @ApiOperation(value = "Delete a schedule by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = "application/hal+json")
    public void deleteById(@PathVariable Long id) {
    }
}
