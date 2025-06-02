package com.zoho.payrollservice.feign;

import com.zoho.payrollservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "EMPLOYEE-SERVICE", url = "http://localhost:8083")
public interface EmployeeClient {

    @GetMapping("/api/employees/{id}")
    public ResponseEntity<UserDto> getEmployee(@PathVariable UUID id);
}
