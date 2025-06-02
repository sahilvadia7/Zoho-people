package com.zoho.payrollservice.controller;


import com.zoho.payrollservice.dto.request.PerformanceRequest;
import com.zoho.payrollservice.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
@Tag(name = "Performance API", description = "Operations related to Employee Performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("/{hrId}")
    @Operation(summary = "add performance by employee ID", description = "add employee performance by their UUID")
    public ResponseEntity<?> addPerformance(@RequestBody PerformanceRequest request, @PathVariable UUID hrId){
        return performanceService.addPerformance(request,hrId);
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "employee performance",description = "fetch employee performance by thier UUID")
    public ResponseEntity<?> getByEmployeeId(@PathVariable UUID employeeId){
        return performanceService.getByEmployeeId(employeeId);
    }

    @PutMapping("/{performanceId}")
    @Operation(summary = "update performance",description="update performance by id")
    public ResponseEntity<?> updatePerformance(@RequestBody PerformanceRequest request, @PathVariable UUID performanceId){
        return performanceService.updatePerformance(request,performanceId);
    }

}
