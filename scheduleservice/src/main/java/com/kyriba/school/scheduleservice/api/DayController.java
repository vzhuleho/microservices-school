package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.domain.entity.Schedule;
import com.kyriba.school.scheduleservice.domain.entity.SchoolClass;
import com.kyriba.school.scheduleservice.service.DayService;
import com.kyriba.school.scheduleservice.service.ScheduleService;
import com.kyriba.school.scheduleservice.service.SchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules/{year}/{grade}/{letter}/days")
@RestController
public class DayController {

    private final DayService dayService;
    private final ScheduleService scheduleService;
    private final SchoolClassService schoolClassService;
    private final ModelMapper mapper;

    public DayController(DayService dayService, ScheduleService scheduleService, SchoolClassService schoolClassService,
                         ModelMapper mapper) {
        this.dayService = dayService;
        this.scheduleService = scheduleService;
        this.schoolClassService = schoolClassService;
        this.mapper = mapper;
    }

    @ApiOperation(value = "Get days by the schedule id")
    @GetMapping(produces = "application/hal+json")
    public Page<DayDTO> getByScheduleId(@PathVariable int year,
                                        @PathVariable int grade,
                                        @PathVariable String letter,
                                        Pageable pageable) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);
        return dayService.findByScheduleId(schedule.getId(), pageable)
            .map(source -> mapper.map(source, DayDTO.class));
    }

    @ApiOperation(value = "Get a day by the schedule id and date", response = Day.class)
    @GetMapping(value = "/{date}", produces = "application/hal+json")
    public DayDTO getDayByScheduleIdAndDate(@PathVariable int year,
                                            @PathVariable int grade,
                                            @PathVariable String letter,
                                            @PathVariable String date) {
        SchoolClass schoolClass = schoolClassService.find(grade, letter, year);
        Schedule schedule = scheduleService.findOrCreate(year, schoolClass);

        Day day = dayService.findByScheduleIdAndDate(schedule.getId(), LocalDate.parse(date));
        return mapper.map(day, DayDTO.class);
    }
}
