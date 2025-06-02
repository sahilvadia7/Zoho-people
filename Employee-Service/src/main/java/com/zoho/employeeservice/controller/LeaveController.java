package com.zoho.employeeservice.controller;

import com.zoho.employeeservice.dto.request.LeaveRequest;
import com.zoho.employeeservice.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leave")
@Tag(name = "Leave API", description = "Manage employee leave requests and approvals")
public class LeaveController {

    private final LeaveService leaveService;

    @Operation(
            summary = "Submit a leave request",
            description = "Allows an employee to request a leave using their employee ID"
    )
    @PostMapping("/{empId}")
    public ResponseEntity<?> leaveRequest(@Valid @RequestBody LeaveRequest request, @PathVariable UUID empId) {
        return leaveService.LeaveRequest(request, empId);
    }

    @Operation(
            summary = "Get all leave requests by employee ID",
            description = "Returns all leave requests submitted by a specific employee"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllLeaveByEmployee(@PathVariable("id") UUID empId) {
        return leaveService.getAllLeaveByEmployee(empId);
    }

    @Operation(
            summary = "Get all leave requests",
            description = "Retrieves all leave requests from all employees"
    )
    @GetMapping("")
    public ResponseEntity<?> getAllLeave() {
        return leaveService.getAllLeave();
    }

    @Operation(
            summary = "Approve a leave request",
            description = "Approves a specific leave request by leave ID and employee ID"
    )
    @PostMapping("{leaveId}/approve/{empId}")
    public ResponseEntity<?> approveLeave(@PathVariable UUID leaveId, @PathVariable UUID empId) {
        return leaveService.leaveApproveById(leaveId, empId);
    }
}
