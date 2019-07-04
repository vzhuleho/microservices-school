package com.kyriba.school.scheduleservice.api;

import com.kyriba.school.scheduleservice.domain.dto.AbsenceDetails;
import com.kyriba.school.scheduleservice.domain.dto.AbsenceRequest;
import com.kyriba.school.scheduleservice.service.AbsenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "School Schedules Management System")
@RequestMapping("/api/v1/absences")
@RestController
@RequiredArgsConstructor
public class AbsenceController {
    
    private final AbsenceService absenceService;

    @ApiOperation(value = "Add information about pupil's absence to a lesson")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public long addAbsenceToLesson(@RequestBody AbsenceRequest absence) {
        return absenceService.addAbsenceToLesson(absence);
    }

    @ApiOperation(value = "Retrieve absence information by id", response = AbsenceDetails.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AbsenceDetails getAbsence(@ApiParam(value = "Absence unique identifier", example = "1", required = true) @PathVariable Long id ) {
        return absenceService.getAbsenceById(id);
    }

    @ApiOperation(value = "Update absence information by id", response = AbsenceDetails.class)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public AbsenceDetails updateAbsence(@RequestBody AbsenceRequest absenceRequest) {
        return absenceService.updateAbsence(absenceRequest);
    }

    @ApiOperation(value = "Delete information about the pupil's absence by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAbsence(@ApiParam(value = "Absence unique identifier", example = "1", required = true) @PathVariable Long id ) {
        absenceService.removeAbsenceById(id);
    }
}
