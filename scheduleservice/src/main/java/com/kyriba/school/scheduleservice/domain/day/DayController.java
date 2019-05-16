package com.kyriba.school.scheduleservice.domain.day;


import com.kyriba.school.scheduleservice.domain.schedule.Schedule;
import com.kyriba.school.scheduleservice.domain.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

import static com.kyriba.school.scheduleservice.infrastructure.Endpoints.SCHEDULES;

@Api(value="School Schedules Management System")
@RequestMapping(SCHEDULES)
@RepositoryRestController
public class DayController {

    private final ScheduleService scheduleService;
    private final DayService dayService;
    private final PagedResourcesAssembler<Day> assembler;

    @Autowired
    public DayController(ScheduleService scheduleService,
                         DayService dayService,
                         PagedResourcesAssembler<Day> assembler) {
        this.scheduleService = scheduleService;
        this.dayService = dayService;
        this.assembler = assembler;
    }

    @ApiOperation(value = "Get days by the schedule id")
    @ResponseBody
    @GetMapping(value = "/{id}/days", produces = "application/hal+json")
    public ResponseEntity<?> getByScheduleId(@PathVariable Long id, Pageable pageable) {

        Schedule schedule = scheduleService.findById(id);
        Page<Day> days = dayService.findBySchedule(schedule, pageable);
        PagedResources<Resource<Day>> resources = assembler.toResource(days);
        return ResponseEntity.ok(resources);
    }

    @ApiOperation(value = "Get a day by the schedule id and date", response = Day.class)
    @ResponseBody
    @GetMapping(value = "/{id}/days/{date}", produces = "application/hal+json")
    public Day getDayByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {

        Schedule schedule = scheduleService.findById(id);
        return dayService.findByScheduleAndDate(schedule, LocalDate.parse(date));
    }
}
