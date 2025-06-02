package com.zoho.employeeservice.service;

import com.zoho.employeeservice.dto.request.LeaveRequest;
import com.zoho.employeeservice.model.Leave;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    ResponseEntity<?> LeaveRequest(LeaveRequest request, UUID empId);
    ResponseEntity<?> getAllLeaveByEmployee(UUID empId);
    ResponseEntity<?> getAllLeave();
    ResponseEntity<?> leaveApproveById(UUID leaveId,UUID empId);
}
