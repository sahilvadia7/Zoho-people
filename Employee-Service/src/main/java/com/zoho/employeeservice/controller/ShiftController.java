package com.zoho.employeeservice.controller;

import com.zoho.employeeservice.dto.request.ShiftRequest;
import com.zoho.employeeservice.service.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shift")
@RequiredArgsConstructor
@Tag(name = "Shift API", description = "Manage shifts and assign them to employees")
public class ShiftController {

    private final ShiftService shiftService;

    @Operation(
            summary = "Add a new shift",
            description = "Creates a new shift with details such as name, time, and working days"
    )
    @PostMapping("")
    public ResponseEntity<?> addShift(@Valid @RequestBody ShiftRequest shiftRequest) {
        return shiftService.addShift(shiftRequest);
    }

    @Operation(
            summary = "Assign shift to employee",
            description = "Assigns a specific shift to an employee using employee ID and shift ID"
    )
    @PostMapping("/{empId}/assign/{shiftId}")
    public ResponseEntity<?> assignShift(@PathVariable UUID empId, @PathVariable UUID shiftId) {
        return shiftService.assignShift(empId, shiftId);
    }

    @Operation(
            summary = "Update shift details",
            description = "Updates the details of an existing shift by shift ID"
    )
    @PutMapping("/{shiftId}")
    public ResponseEntity<?> updateShift(@PathVariable UUID shiftId, @RequestBody ShiftRequest shiftRequest) {
        return shiftService.updateShift(shiftId, shiftRequest);
    }
}
