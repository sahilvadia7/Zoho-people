package com.zoho.payrollservice.controller;

import com.zoho.payrollservice.dto.request.PayrollRequest;
import com.zoho.payrollservice.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
@Tag(name = "Payroll Management", description = "APIs for managing employee payroll records")
public class PayrollController {

    private final PayrollService payrollService;

    @Operation(summary = "Create a new payroll record", description = "Adds a new payroll entry for an employee, calculating total in-hand salary based on expenses.")
    @PostMapping
    public ResponseEntity<?> addPayroll(@RequestBody PayrollRequest request){
        return payrollService.createPayroll(request);
    }

    @Operation(summary = "Get payroll by ID", description = "Retrieves a specific payroll record using its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<?> payrollById(@PathVariable UUID id){
        return payrollService.payrollById(id);
    }

    @Operation(summary = "Update a payroll record", description = "Modifies an existing payroll record identified by its UUID.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayroll(@PathVariable UUID id, @RequestBody PayrollRequest request){
        return payrollService.updatePayroll(id,request);
    }
}
