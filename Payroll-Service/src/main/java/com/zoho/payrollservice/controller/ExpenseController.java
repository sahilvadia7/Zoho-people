package com.zoho.payrollservice.controller;


import com.zoho.payrollservice.dto.request.ExpenseRequest;
import com.zoho.payrollservice.enums.Status;
import com.zoho.payrollservice.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@Tag(name = "Expense Management", description = "APIs for managing employee expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "Add a new expense", description = "Creates and saves a new expense record for an employee.")
    @PostMapping
    public ResponseEntity<?> addExpense(@Valid @RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @Operation(summary = "Update an existing expense", description = "Modifies an expense record identified by its UUID.")
    @PutMapping("/{expenseId}")
    public ResponseEntity<?> updateExpense(
            @PathVariable UUID expenseId,
            @Valid @RequestBody ExpenseRequest request) {
        return expenseService.updateExpense(request, expenseId);
    }


    @Operation(summary = "Delete an expense", description = "Removes an expense record from the database by its UUID.")
    @DeleteMapping("/{expenseId}") // Maps HTTP DELETE requests to /api/expenses/{expenseId}
    public ResponseEntity<?> deleteExpense(@PathVariable UUID expenseId) {
        return expenseService.deleteExpense(expenseId);
    }


    @Operation(summary = "Get expenses by employee ID", description = "Retrieves all expense records for a given employee.")
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getExpensesByEmployeeId(@PathVariable UUID employeeId) {
        return expenseService.expenseByEmployeeId(employeeId);
    }


    @Operation(summary = "Approve an expense", description = "Approves a specific expense record. Requires HR privileges.")
    @PutMapping("/approve/{expenseId}")
    public ResponseEntity<?> approveExpense(
            @PathVariable UUID expenseId,
            @RequestParam UUID hrId,
            @Valid @RequestParam Status status) {
        return expenseService.approveExpense(expenseId, status, hrId);
    }
}