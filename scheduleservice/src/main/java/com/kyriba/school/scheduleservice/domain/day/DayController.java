package com.kyriba.school.scheduleservice.domain.day;


import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleProjection;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleService;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;

@Api(value="School Schedules Management System")
@RestController // only needed for Swagger
@RepositoryRestController
public class DayController {

    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final DayService dayService;
    private final ProjectionFactory factory;
    private final PagedResourcesAssembler<Day> assembler;

    @Autowired
    public DayController(ScheduleService scheduleService, SchoolClassService schoolClassService,
                         DayService dayService, ProjectionFactory factory,
                         PagedResourcesAssembler<Day> assembler) {
        this.scheduleService = scheduleService;
        this.schoolClassService = schoolClassService;
        this.dayService = dayService;
        this.factory = factory;
        this.assembler = assembler;
    }

    @ApiOperation(value = "Get days by the schedule id")
    @ResponseBody
    @GetMapping(value = SCHEDULES + "/{id}/days", produces = "application/hal+json")
    public ResponseEntity<?> getByScheduleId(@PathVariable Long id, Pageable pageable) {

        Schedule schedule = scheduleService.findById(id);
        Page<Day> days = dayService.findBySchedule(schedule, pageable);
        PagedResources<Resource<Day>> resources = assembler.toResource(days);
        return ResponseEntity.ok(resources);
    }

    @ApiOperation(value = "Get a day by the schedule id and date", response = Day.class)
    @ResponseBody
    @GetMapping(value = SCHEDULES + "/{id}/days/{date}", produces = "application/hal+json")
    public Day getDayByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {

        Schedule schedule = scheduleService.findById(id);
        return dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
    }

    /*@ApiOperation(value = "Update an existing schedule", response = ScheduleProjection.class)
    @ResponseBody
    @PatchMapping(value = SCHEDULES, produces = "application/hal+json")
    public Resource<ScheduleProjection> updateSchedule(@RequestBody Schedule scheduleToUpdate) {
        scheduleToUpdate.setSchoolClass(schoolClassService.findBySchedule(scheduleToUpdate));
        Schedule updatedSchedule = scheduleService.save(scheduleToUpdate);
        return getProjectionResource(updatedSchedule);

    }

    @ApiOperation(value = "Delete a schedule by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/schedules/{id}", produces = "application/hal+json")
    public void deleteById(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }



    private Resource<ScheduleProjection> getProjectionResource(Schedule schedule) {
        ScheduleProjection scheduleProjection = factory.createProjection(ScheduleProjection.class, schedule);
        return new Resource<>(scheduleProjection);
    }*/
}
