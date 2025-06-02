package com.zoho.employeeservice.controller;

import com.zoho.employeeservice.dto.request.EmployeeRequest;
import com.zoho.employeeservice.model.Employee;
import com.zoho.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "Operations related to Employee management")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create a new employee", description = "Adds a new employee to the database")
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequest employee) {
        return employeeService.create(employee);
    }

    @Operation(summary = "Get employee by ID", description = "Fetch a single employee by their UUID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable UUID id) {
        return employeeService.getById(id);
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return employeeService.getAll();
    }

    @Operation(summary = "Update an employee", description = "Update details of an existing employee by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable UUID id, @RequestBody Employee updatedEmployee) {
        return employeeService.update(id, updatedEmployee);
    }

    @Operation(summary = "Delete an employee", description = "Remove an employee from the system by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
        return employeeService.delete(id);
    }
}
