package com.kyriba.school.scheduleservice.api;

import com.kyriba.school.scheduleservice.domain.dto.MarkDetails;
import com.kyriba.school.scheduleservice.domain.dto.MarkRequest;
import com.kyriba.school.scheduleservice.service.MarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(value = "School Schedules Management System")
@RequestMapping(value = "/api/v1/marks",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @ApiOperation(value = "Retrieve mark information by id", response = MarkDetails.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MarkDetails getMark(@ApiParam(value = "Mark unique identifier", example = "1", required = true) @PathVariable Long id) {
        return markService.getMarkById(id);
    }

    @ApiOperation(value = "Add information about pupil's mark to a lesson", response = long.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public long addMarkToLesson(@RequestBody MarkRequest markRequest) {
        return markService.addMarkToLesson(markRequest);
    }

    @ApiOperation(value = "Update mark information by id", response = MarkDetails.class)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public MarkDetails updateMark(@RequestBody MarkRequest markRequest) {
        return markService.updateMark(markRequest);
    }

    @ApiOperation(value = "Delete information about the mark by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMark(@ApiParam(value = "Mark unique identifier", example = "1", required = true) @PathVariable Long id) {
        markService.removeMarkById(id);
    }
}
