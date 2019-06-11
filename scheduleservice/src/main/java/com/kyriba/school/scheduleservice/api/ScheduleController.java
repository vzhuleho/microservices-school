package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.ScheduleDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import com.kyriba.school.scheduleservice.service.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(value="School Schedules Management System")
@RequestMapping(value = "/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final ModelMapper mapper;

    public ScheduleController(ScheduleService scheduleService, SchoolClassService schoolClassService, ModelMapper mapper) {
        this.scheduleService = scheduleService;
        this.schoolClassService = schoolClassService;
        this.mapper = mapper;
    }

    @ApiOperation(value = "View a list of available schedules")
    @GetMapping
    public Page<ScheduleDTO> getAllSchedules(Pageable pageable) {
        return scheduleService.findAll(pageable).map(schedule -> mapper.map(schedule, ScheduleDTO.class));
    }

    @ApiOperation(value = "Get a schedule by id", response = ScheduleDTO.class)
    @GetMapping("/{id}")
    public ScheduleDTO getById(@PathVariable Long id) {
        //IllegalArgumentException: Name for argument type [java.lang.Long] not available, and parameter name information not found in class file either.
        Schedule schedule = scheduleService.findById(id);
        return mapper.map(schedule, ScheduleDTO.class);
    }

    @ApiOperation(value = "Create a new schedule", response = Long.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public long create(@RequestBody ScheduleDTO scheduleToCreate) {
        SchoolClassDTO schoolClassToFind = scheduleToCreate.getSchoolClass();
        int year = scheduleToCreate.getYear();
        SchoolClass schoolClass = schoolClassService.find(schoolClassToFind.getGrade(), schoolClassToFind.getLetter(), year);
        return scheduleService.findOrCreate(year, schoolClass).getId();
    }

    @ApiOperation(value = "Get a schedule or create a new one if the schedule for the requested year and school class doesn't exist",
            response = ScheduleDTO.class)
    @GetMapping("/{year}/{grade}/{letter}")
    public ScheduleDTO getOrCreate(@PathVariable int year,
                                   @PathVariable int grade,
                                   @PathVariable String letter) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        return mapper.map(schedule, ScheduleDTO.class);
    }

    @ApiOperation(value = "Update an existing schedule", response = ScheduleDTO.class)
    @PutMapping
    public ScheduleDTO updateSchedule(@Valid @RequestBody ScheduleDTO scheduleToUpdate) {
        SchoolClassDTO schoolClassToFind = scheduleToUpdate.getSchoolClass();
        int year = scheduleToUpdate.getYear();
        SchoolClass schoolClass = schoolClassService.find(schoolClassToFind.getGrade(), schoolClassToFind.getLetter(), year);
        Schedule schedule = mapper.map(scheduleToUpdate, Schedule.class);
        schedule.setSchoolClass(schoolClass);
        return mapper.map(scheduleService.save(schedule), ScheduleDTO.class);
    }

    @ApiOperation(value = "Delete a schedule by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
    }
}
