package com.zoho.employeeservice.service;

import com.zoho.employeeservice.dto.request.TimesheetRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

public interface TimesheetService {

    ResponseEntity<?> addLog(TimesheetRequest request);
    ResponseEntity<?> getTimesheetByEmployeeId(UUID empId);
    ResponseEntity<?> updateTimesheet(TimesheetRequest request,UUID timesheetId);
    ResponseEntity<?> deleteTimesheet(UUID timesheetId);
    ResponseEntity<?> getAllTimesheet();
    ResponseEntity<?> getAllTimesheetByDate(Date date);

}
