package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.ScheduleDetails;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleRequest;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @ApiOperation(value = "View a list of available schedules")
    @GetMapping
    public Page<ScheduleDetails> getAllSchedules(Pageable pageable) {
        return scheduleService.findAll(pageable);
    }

    @ApiOperation(value = "Get a schedule by id", response = ScheduleDetails.class)
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ScheduleDetails getById(@PathVariable Long id) {
        return scheduleService.findById(id);
    }

    @ApiOperation(value = "Create a new schedule", response = ScheduleDetails.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/hal+json")
    public long create(@RequestBody ScheduleRequest scheduleToCreate) {
        return scheduleService.create(scheduleToCreate).getId();
    }

    @ApiOperation(value = "Get a schedule by its year and grade and letter of its school class",
            response = ScheduleDetails.class)
    @GetMapping(value = "/{year}/{grade}/{letter}", produces = "application/hal+json")
    public ScheduleDetails get(@PathVariable int year,
                               @PathVariable int grade,
                               @PathVariable String letter) {
        return scheduleService.find(year, grade, letter);
    }

    @ApiOperation(value = "Delete a schedule by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = "application/hal+json")
    public void deleteById(@PathVariable Long id) {
        scheduleService.delete(id);
    }
}
