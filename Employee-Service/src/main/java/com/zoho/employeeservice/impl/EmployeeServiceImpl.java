package com.zoho.employeeservice.impl;

import com.zoho.employeeservice.dto.UserDto;
import com.zoho.employeeservice.dto.request.EmployeeRequest;
import com.zoho.employeeservice.dto.response.FullEmployeeDetailResponse;
import com.zoho.employeeservice.feign.AuthClient;
import com.zoho.employeeservice.model.Employee;
import com.zoho.employeeservice.repository.EmployeeRepository;
import com.zoho.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthClient authClient;


    @Override
    public ResponseEntity<?> create(EmployeeRequest request) {
        ResponseEntity<?> response = authClient.fetchUser(request.getEmployeeId());

        if (response.getStatusCode().isError() || response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID not found in Auth Service");
        }

        Employee employee = Employee.builder()
                .employeeId(request.getEmployeeId())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .phone(request.getPhone())
                .employeeCode(request.getEmployeeCode())
                .shiftId(request.getShiftId())
                .dateOfJoining(new Date())
                .leaveCount(request.getLeaveCount())
                .build();

        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getById(UUID id) {
        ResponseEntity<UserDto> authResponse = authClient.fetchUser(id);
//        System.out.println("response ::::: " + authResponse); // This will still show the full ResponseEntity


        if (authResponse.getStatusCode().isError() || authResponse.getBody() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to fetch user details from Auth Service or user not found.");
        }

        if (!(authResponse.getBody() instanceof UserDto)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected response type from Auth Service. (Internal client configuration error)");
        }
        UserDto userDto = authResponse.getBody();
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee details not found for the given ID in the Employee Service.");
        }

        Employee employee = employeeOptional.get();


        FullEmployeeDetailResponse fullResponse = FullEmployeeDetailResponse.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .organizationId(userDto.getOrganizationId())
                .designation(employee.getDesignation())
                .department(employee.getDepartment())
                .phone(employee.getPhone())
                .dateOfJoining(employee.getDateOfJoining())
                .employeeCode(employee.getEmployeeCode())
                .build();

        return new ResponseEntity<>(fullResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(UUID id, Employee updatedEmployee) {
        Optional<Employee> optionalEmp = employeeRepository.findById(id);
        if (optionalEmp.isPresent()) {
            Employee emp = optionalEmp.get();
            emp.setDepartment(updatedEmployee.getDepartment());
            emp.setDesignation(updatedEmployee.getDesignation());
            emp.setPhone(updatedEmployee.getPhone());
            emp.setDateOfJoining(updatedEmployee.getDateOfJoining());
            emp.setEmployeeCode(updatedEmployee.getEmployeeCode());
            return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }
}
