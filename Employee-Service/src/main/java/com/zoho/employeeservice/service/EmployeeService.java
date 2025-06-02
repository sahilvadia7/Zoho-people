package com.zoho.employeeservice.service;

import com.zoho.employeeservice.dto.request.EmployeeRequest;
import com.zoho.employeeservice.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface EmployeeService {

    ResponseEntity<?> create(EmployeeRequest employee);
    ResponseEntity<?> getById(UUID id);
    ResponseEntity<?> getAll();
    ResponseEntity<?> update(UUID id, Employee updatedEmployee);
    ResponseEntity<?> delete(UUID id);
}
