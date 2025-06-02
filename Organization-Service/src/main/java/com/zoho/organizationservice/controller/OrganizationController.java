package com.zoho.organizationservice.controller;

import com.zoho.organizationservice.dto.request.LicenseRequest;
import com.zoho.organizationservice.dto.request.OrganizationRequestDTO;
import com.zoho.organizationservice.service.LicenseService;
import com.zoho.organizationservice.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @Operation(description = "Create new organization with admin list")
    public ResponseEntity<?> create(@RequestBody OrganizationRequestDTO dto) {
        return organizationService.createOrganization(dto);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get organization by ID")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return organizationService.getOrganizationById(id);
    }

    @GetMapping
    @Operation(description = "Get all organizations")
    public ResponseEntity<?> getAll() {
        return organizationService.getAllOrganizations();
    }

    @PutMapping("/approve/{orgId}")
    @Operation(description = "Approve organization by Super Admin")
    public ResponseEntity<?> approveOrg(
            @PathVariable UUID orgId,
            @RequestHeader("X-USER-ID") UUID userId) {
        return organizationService.approveOrganization(orgId, userId);
    }





}

