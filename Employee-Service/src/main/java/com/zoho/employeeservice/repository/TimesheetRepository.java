package com.zoho.employeeservice.repository;

import com.zoho.employeeservice.dto.request.TimesheetRequest;
import com.zoho.employeeservice.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, UUID> {
    List<Timesheet> findByEmployeeId(UUID empId);
    List<Timesheet> findByDate(Date date);

}
