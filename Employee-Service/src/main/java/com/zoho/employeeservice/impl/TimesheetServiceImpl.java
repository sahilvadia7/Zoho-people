package com.zoho.employeeservice.impl;

import com.zoho.employeeservice.dto.request.TimesheetRequest;
import com.zoho.employeeservice.model.Employee;
import com.zoho.employeeservice.model.Timesheet;
import com.zoho.employeeservice.repository.EmployeeRepository;
import com.zoho.employeeservice.repository.TimesheetRepository;
import com.zoho.employeeservice.service.EmployeeService;
import com.zoho.employeeservice.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.zoho.employeeservice.constrain.GlobalConstrain.*;

@Service
@RequiredArgsConstructor
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> addLog(TimesheetRequest request) {

        Employee isPresent = employeeRepository.findById(request.getEmployeeId()).orElse(null);

        if (isPresent!=null){
            Timesheet timesheet = Timesheet.builder()
                    .employeeId(request.getEmployeeId())
                    .tasks(request.getTasks())
                    .date(request.getDate())
                    .hoursWorked(request.getHoursWorked())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();

            return new ResponseEntity<>(timesheetRepository.save(timesheet), HttpStatus.OK);
        }
        return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getTimesheetByEmployeeId(UUID empId) {
        List<Timesheet> timesheets = timesheetRepository.findByEmployeeId(empId);

        if (timesheets.isEmpty()) {
            return new ResponseEntity<>("No timesheets found for employee ID: " + empId, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(timesheets, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> updateTimesheet(TimesheetRequest request,UUID timesheetId) {
        Timesheet existingTimesheet = timesheetRepository.findById(timesheetId).orElse(null);

        if(existingTimesheet!=null){

            if(request.getHoursWorked()!=null){
                existingTimesheet.setHoursWorked(request.getHoursWorked());
            }
            if(request.getTasks()!=null){
                existingTimesheet.setTasks(request.getTasks());
            }
            if(request.getDate()!=null){
                existingTimesheet.setDate(request.getDate());
            }
            existingTimesheet.setUpdatedAt(new Date());

            Timesheet updatedTimesheet = timesheetRepository.save(existingTimesheet);
            return new ResponseEntity<>(updatedTimesheet,HttpStatus.OK);
        }
        return new ResponseEntity<>(TIMESHEET_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> deleteTimesheet(UUID timesheetId) {
        Timesheet isExist = timesheetRepository.findById(timesheetId).orElse(null);

        if(isExist!=null){
            timesheetRepository.deleteById(timesheetId);
            return new ResponseEntity<>(TIMESHEET_DELETED,HttpStatus.OK);
        }
        return new ResponseEntity<>(TIMESHEET_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getAllTimesheet() {
        List<Timesheet> allTimesheet = timesheetRepository.findAll();

        if(!allTimesheet.isEmpty()){
            return new ResponseEntity<>(allTimesheet,HttpStatus.OK);
        }
        return new ResponseEntity<>(TIMESHEET_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getAllTimesheetByDate(Date date) {
        List<Timesheet> timesheets = timesheetRepository.findByDate(date);

        if (timesheets.isEmpty()) {
            return new ResponseEntity<>(TIMESHEET_NOT_FOUND+": "+ date, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(timesheets, HttpStatus.OK);
        }
    }
}
