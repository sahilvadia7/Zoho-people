package com.zoho.payrollservice.service;

import com.zoho.payrollservice.dto.request.PerformanceRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PerformanceService {

    ResponseEntity<?> addPerformance(PerformanceRequest request,UUID hrId);
    ResponseEntity<List<?>> getByEmployeeId(UUID id);
    ResponseEntity<?> updatePerformance(PerformanceRequest request,UUID id);
}
