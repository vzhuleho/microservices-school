package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import com.kyriba.school.scheduleservice.service.DayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @ApiOperation(value = "Get days for a schedule")
    @GetMapping(produces = "application/hal+json")
    public Page<DayDTO> getByScheduleId(@PathVariable int year,
                                        @PathVariable int grade,
                                        @PathVariable String letter,
                                        Pageable pageable) {
        return dayService.findAll(year, grade, letter, pageable);
    }

    @ApiOperation(value = "Get a day by the schedule id and date", response = Day.class)
    @GetMapping(value = "/{date}", produces = "application/hal+json")
    public DayDTO getDayByScheduleIdAndDate(@PathVariable int year,
                                            @PathVariable int grade,
                                            @PathVariable String letter,
                                            @PathVariable String date) {
        return dayService.find(year, grade, letter, LocalDate.parse(date));
    }
}
