package com.zoho.employeeservice.controller;

import com.zoho.employeeservice.dto.request.TimesheetRequest;
import com.zoho.employeeservice.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/timesheet")
@RequiredArgsConstructor
@Tag(name = "Timesheet API", description = "Manages timesheet entries for employees")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Operation(summary = "Add a timesheet log", description = "Create a new log entry for employee work hours.")
    @PostMapping("")
    public ResponseEntity<?> addLog(@Valid @RequestBody TimesheetRequest request){
        return timesheetService.addLog(request);
    }

    @Operation(summary = "Get logs by employee ID", description = "Fetch all timesheet entries for a specific employee.")
    @GetMapping("/{empId}")
    public ResponseEntity<?> getLog(@PathVariable UUID empId){
        return timesheetService.getTimesheetByEmployeeId(empId);
    }

    @Operation(summary = "Update a timesheet entry", description = "Update an existing timesheet record by its ID.")
    @PutMapping("/{timesheetId}")
    public ResponseEntity<?> updateTimesheet(@Valid @RequestBody TimesheetRequest request, @PathVariable UUID timesheetId){
        return timesheetService.updateTimesheet(request, timesheetId);
    }

    @Operation(summary = "Delete a timesheet entry", description = "Delete a timesheet record by its ID.")
    @DeleteMapping("/{timesheetId}")
    public ResponseEntity<?> deleteTimesheet(@PathVariable UUID timesheetId){
        return timesheetService.deleteTimesheet(timesheetId);
    }

    @Operation(summary = "Get all timesheet entries", description = "Fetch all timesheet logs for all employees.")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return timesheetService.getAllTimesheet();
    }

    @Operation(summary = "Get timesheets by date", description = "Fetch all timesheet logs for a specific date.")
    @GetMapping("date/{date}")
    public ResponseEntity<?> getByDate(
            @Parameter(description = "Date in format yyyy-MM-dd HH:mm:ss")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
        return timesheetService.getAllTimesheetByDate(date);
    }
}
