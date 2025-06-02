package com.zoho.organizationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "AUTH-SERVICE", url = "http://localhost:8081")
public interface AuthClient {

    @GetMapping("/auth/validate-role/{userId}")
    ResponseEntity<?> getRole(@PathVariable("userId") UUID userId);

    @GetMapping("/auth/organization/validate-access/{userId}/{orgId}")
    public ResponseEntity<?> checkUserAccessToOrg(@PathVariable UUID userId, @PathVariable UUID orgId);
}
