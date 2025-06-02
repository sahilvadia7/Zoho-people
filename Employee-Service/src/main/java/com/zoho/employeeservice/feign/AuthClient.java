package com.zoho.employeeservice.feign;

import com.zoho.employeeservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "USERSERVICE", url = "http://localhost:8080")
public interface AuthClient {

    @GetMapping("/auth/fetchUser/{id}")
    public ResponseEntity<UserDto> fetchUser(@PathVariable("id") UUID userId);


}
