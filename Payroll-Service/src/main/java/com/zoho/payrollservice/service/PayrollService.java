package com.zoho.payrollservice.service;

import com.zoho.payrollservice.dto.request.PayrollRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PayrollService {
    ResponseEntity<?> createPayroll(PayrollRequest request);
    ResponseEntity<?> payrollById(UUID id);
    ResponseEntity<?> updatePayroll(UUID id,PayrollRequest request);
}
