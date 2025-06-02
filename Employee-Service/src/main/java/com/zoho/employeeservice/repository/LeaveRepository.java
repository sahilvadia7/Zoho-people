package com.zoho.employeeservice.repository;

import com.zoho.employeeservice.dto.request.LeaveRequest;
import com.zoho.employeeservice.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, UUID> {
   List<Leave> findByEmployeeId(UUID id);
}
