package com.kyriba.school.scheduleservice.api;

import com.kyriba.school.scheduleservice.domain.dto.MarkDTO;
import com.kyriba.school.scheduleservice.service.MarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "School Schedules Management System")
@RequestMapping("/api/v1/marks/{id}")
@RestController
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @ApiOperation(value = "Retrieve mark information by id", response = MarkDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MarkDTO getMark(@ApiParam(value = "Mark unique identifier", example = "1", required = true) @PathVariable Long id) {
        return markService.getMarkById(id);
    }

    @ApiOperation(value = "Update mark information by id", response = Long.class)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public Long updateMark(@ApiParam(value = "Mark unique identifier", example = "1", required = true) @PathVariable Long id, @RequestBody MarkDTO markDTO) {
        return markService.updateMarkById(id, markDTO);
    }

    @ApiOperation(value = "Delete information about the mark by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteMark(@ApiParam(value = "Mark unique identifier", example = "1", required = true) @PathVariable Long id) {
        markService.removeMarkById(id);
    }
}
