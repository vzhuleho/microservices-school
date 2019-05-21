package com.kyriba.school.scheduleservice.api;


import com.kyriba.school.scheduleservice.domain.dto.DayDTO;
import com.kyriba.school.scheduleservice.domain.dto.ScheduleDTO;
import com.kyriba.school.scheduleservice.domain.dto.SchoolClassDTO;
import com.kyriba.school.scheduleservice.domain.entity.Day;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@Api(value="School Schedules Management System")
@RequestMapping("/api/v1/schedules")
@RestController
public class DayController {

    @ApiOperation(value = "Get days by the schedule id")
    @GetMapping(value = "/{id}/days", produces = "application/hal+json")
    public Page<DayDTO> getByScheduleId(@PathVariable Long id, Pageable pageable) {
        return new PageImpl<>(Collections.singletonList(/*new DayDTO(LocalDate.now(), new ScheduleDTO(1L, 2019,
            new SchoolClassDTO(1L, 1, "A", 2019)), Collections.emptyList())*/new DayDTO()));
    }

    @ApiOperation(value = "Get a day by the schedule id and date", response = Day.class)
    @GetMapping(value = "/{id}/days/{date}", produces = "application/hal+json")
    public DayDTO getDayByScheduleIdAndDate(@PathVariable Long id, @PathVariable String date) {
        return new DayDTO();
//        return new DayDTO(LocalDate.now(), new ScheduleDTO(1L, 2019,
//            new SchoolClassDTO(1L, 1, "A", 2019)), Collections.emptyList());
    }
}
