package com.zoho.employeeservice.impl;

import com.zoho.employeeservice.dto.UserDto;
import com.zoho.employeeservice.dto.request.LeaveRequest;
import com.zoho.employeeservice.enums.Role;
import com.zoho.employeeservice.enums.Status;
import com.zoho.employeeservice.feign.AuthClient;
import com.zoho.employeeservice.model.Employee;
import com.zoho.employeeservice.model.Leave;
import com.zoho.employeeservice.repository.EmployeeRepository;
import com.zoho.employeeservice.repository.LeaveRepository;
import com.zoho.employeeservice.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zoho.employeeservice.constrain.GlobalConstrain.*;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthClient authClient;

    @Override
    public ResponseEntity<?> LeaveRequest(LeaveRequest request, UUID empId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);


        if(employeeOptional.isPresent()){
            Integer currentLeaveCredit = employeeOptional.get().getLeaveCount();
            System.out.println("current leave count :"+currentLeaveCredit);
            if(currentLeaveCredit>0){

                Leave leave = Leave.builder()
                        .employeeId(empId)
                        .leaveType(request.getLeaveType())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .status(request.getStatus())
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();

                employeeOptional.get().setLeaveCount(currentLeaveCredit-1);
                employeeRepository.save(employeeOptional.get());

                return new ResponseEntity<>(leaveRepository.save(leave),HttpStatus.OK);
            }
            return new ResponseEntity<>(CREDIT_EXCITED, HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(EMPLOYEE_NOT_FOUND,HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> getAllLeaveByEmployee(UUID empId) {
        List<Leave> getAllLeave = leaveRepository.findByEmployeeId(empId);
        if(getAllLeave!=null){
            return new ResponseEntity<>(getAllLeave,HttpStatus.OK);
        }
        return new ResponseEntity<>(EMPLOYEE_NOT_FOUND,HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<?> getAllLeave(){
        return new ResponseEntity<>(leaveRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> leaveApproveById(UUID leaveId,UUID empId) {
        Leave request = leaveRepository.findById(leaveId).orElse(null);
        if(request!=null){

            try {
                UserDto user = authClient.fetchUser(empId).getBody();
                if(user != null){
                    if(user.getRole() == Role.ADMIN || user.getRole() == Role.HR){
                        request.setStatus(Status.ACCEPTED);
                        request.setUpdatedAt(Date.from(Instant.now()));
                        leaveRepository.save(request);
                        return new ResponseEntity<>(APPROVED,HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(FORBIDDEN,HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
                }
            } catch (RestClientException e) {
                return new ResponseEntity<>("Authentication service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
            }
            }
            return new ResponseEntity<>(LEAVE_NOT_FOUND,HttpStatus.NOT_FOUND);
    }


}
