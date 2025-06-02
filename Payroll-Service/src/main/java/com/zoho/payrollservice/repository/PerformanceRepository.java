package com.zoho.payrollservice.repository;

import com.zoho.payrollservice.dto.response.PerformanceResponse;
import com.zoho.payrollservice.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    List<Performance> findByEmployeeId(UUID id);
}
