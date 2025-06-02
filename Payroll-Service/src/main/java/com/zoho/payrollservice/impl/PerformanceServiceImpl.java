package com.zoho.payrollservice.impl;

import com.zoho.payrollservice.dto.UserDto;
import com.zoho.payrollservice.dto.request.PerformanceRequest;
import com.zoho.payrollservice.dto.response.PerformanceResponse;
import com.zoho.payrollservice.enums.Role;
import com.zoho.payrollservice.feign.EmployeeClient;
import com.zoho.payrollservice.model.Performance;
import com.zoho.payrollservice.repository.PerformanceRepository;
import com.zoho.payrollservice.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.zoho.payrollservice.constrain.GlobalConstrain.*;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final EmployeeClient employeeClient;

    @Override
    public ResponseEntity<?> addPerformance(PerformanceRequest request, UUID hrId) {

        ResponseEntity<UserDto> checkHrResponse ;
        try {
            checkHrResponse = employeeClient.getEmployee(hrId);
        } catch (Exception e) {
            System.err.println("Error calling employeeClient for HR ID " + hrId + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to connect to employee service or invalid HR ID format.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (checkHrResponse == null || !checkHrResponse.hasBody() || checkHrResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>("HR with ID " + hrId + " not found or service unavailable.", HttpStatus.NOT_FOUND);
        }

        UserDto hr = checkHrResponse.getBody();
        if (hr == null || !Role.HR.equals(hr.getRole())) {
            return new ResponseEntity<>("Access forbidden. Only HR users can add performance records.", HttpStatus.FORBIDDEN);
        }

        ResponseEntity<UserDto> checkEmpResponse;
        try {
            checkEmpResponse = employeeClient.getEmployee(request.getEmployeeId());
        } catch (Exception e) {
            System.err.println("Error calling employeeClient for Employee ID " + request.getEmployeeId() + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to connect to employee service or invalid Employee ID format.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (checkEmpResponse == null || !checkEmpResponse.hasBody() || checkEmpResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>("Employee with ID " + request.getEmployeeId() + " not found or service unavailable.", HttpStatus.NOT_FOUND);
        }

        Performance performance = Performance.builder()
                .employeeId(request.getEmployeeId())
                .rating(request.getRating())
                .feedback(request.getFeedback())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        Performance savedPerformance = performanceRepository.save(performance);
        return new ResponseEntity<>(savedPerformance, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<?>> getByEmployeeId(UUID id) {
        List<Performance> performances = performanceRepository.findByEmployeeId(id);

        if (performances.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }
        List<PerformanceResponse> allPerformanceResponses = performances.stream()
                .map(performance -> {
                    return PerformanceResponse.builder()
                            .employeeId(performance.getEmployeeId())
                            .rating(performance.getRating())
                            .createdAt(new Date())
                            .feedback(performance.getFeedback())
                            .updatedAt(new Date())
                    .build();
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(allPerformanceResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePerformance(PerformanceRequest request,UUID id) {

        Optional<Performance> existingPerformance = performanceRepository.findById(id);

        if(existingPerformance.isPresent()){

            Performance performance = existingPerformance.get();

            if(request.getEmployeeId()!=null){
                performance.setEmployeeId(request.getEmployeeId());
            }
            if(request.getRating()!=null){
                performance.setRating(request.getRating());
            }
            if(request.getFeedback()!=null){
                performance.setFeedback(request.getFeedback());
            }

            performance.setUpdatedAt(new Date());

            Performance updatedRequest = performanceRepository.save(performance);

            return new ResponseEntity<>(updatedRequest,HttpStatus.OK);
        }
        return new ResponseEntity<>(PERFORMANCE_NOT_FOUND,HttpStatus.OK);
    }

}
