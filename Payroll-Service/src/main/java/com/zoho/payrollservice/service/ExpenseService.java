package com.zoho.payrollservice.service;

import com.zoho.payrollservice.dto.request.ExpenseRequest;
import com.zoho.payrollservice.enums.Status;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ExpenseService {

    ResponseEntity<?> addExpense(ExpenseRequest request);
    ResponseEntity<?> updateExpense(ExpenseRequest request, UUID id);
    ResponseEntity<?> deleteExpense(UUID id);
    ResponseEntity<?> expenseByEmployeeId(UUID id);
    ResponseEntity<?> approveExpense(UUID expenseId, Status status, UUID id);
}
