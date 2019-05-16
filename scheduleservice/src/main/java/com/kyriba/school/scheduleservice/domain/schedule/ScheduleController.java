package com.kyriba.school.scheduleservice.domain.schedule;


import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;

@Api(value="School Schedules Management System")
@RequestMapping(SCHEDULES)
@RepositoryRestController
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final ProjectionFactory factory;
    private final PagedResourcesAssembler<ScheduleProjection> assembler;

    @Autowired
    public ScheduleController(ScheduleService scheduleService,
                              SchoolClassService schoolClassService,
                              ProjectionFactory factory,
                              PagedResourcesAssembler<ScheduleProjection> assembler) {
        this.scheduleService = scheduleService;
        this.schoolClassService = schoolClassService;
        this.factory = factory;
        this.assembler = assembler;
    }

    @ApiOperation(value = "View a list of available schedules")
    @ResponseBody
    @GetMapping
    public ResponseEntity<?> getAllSchedules(Pageable pageable) {
        Page<Schedule> schedules = scheduleService.findAll(pageable);
        Page<ScheduleProjection> projected = schedules.map(schedule -> factory.createProjection(ScheduleProjection.class, schedule));
        PagedResources<Resource<ScheduleProjection>> resources = assembler.toResource(projected);
        return ResponseEntity.ok(resources);

    }

    @ApiOperation(value = "Get a schedule by id", response = ScheduleProjection.class)
    @ResponseBody
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public Resource<ScheduleProjection> getById(@PathVariable Long id) {

        Schedule schedule = scheduleService.findById(id);
        return getProjectionResource(schedule);
    }

    @ApiOperation(value = "Create a new schedule", response = ScheduleProjection.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(produces = "application/hal+json")
    public Resource<ScheduleProjection> create(@RequestBody Schedule scheduleToCreate) {
        SchoolClass schoolClass = schoolClassService.findBySchedule(scheduleToCreate);
        Schedule schedule = scheduleService.findOrCreate(scheduleToCreate.getYear(), schoolClass);
        return getProjectionResource(schedule);
    }

    @ApiOperation(value = "Get a schedule or create a new one if the schedule for the requested year and school class doesn't exist",
            response = ScheduleProjection.class)
    @ResponseBody
    @GetMapping(value = "/{year}/{grade}/{letter}", produces = "application/hal+json")
    public Resource<ScheduleProjection> getOrCreate(@PathVariable int year,
                                @PathVariable int grade,
                                @PathVariable String letter) {

        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        return getProjectionResource(schedule);
    }

    @ApiOperation(value = "Update an existing schedule", response = ScheduleProjection.class)
    @ResponseBody
    @PatchMapping(produces = "application/hal+json")
    public Resource<ScheduleProjection> updateSchedule(@RequestBody Schedule scheduleToUpdate) {
        scheduleToUpdate.setSchoolClass(schoolClassService.findBySchedule(scheduleToUpdate));
        Schedule updatedSchedule = scheduleService.save(scheduleToUpdate);
        return getProjectionResource(updatedSchedule);

    }

    @ApiOperation(value = "Delete a schedule by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = "application/hal+json")
    public void deleteById(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }



    private Resource<ScheduleProjection> getProjectionResource(Schedule schedule) {
        ScheduleProjection scheduleProjection = factory.createProjection(ScheduleProjection.class, schedule);
        return new Resource<>(scheduleProjection);
    }
}
