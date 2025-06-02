package com.zoho.organizationservice.service;


import com.zoho.organizationservice.dto.request.OrganizationRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OrganizationService {
    ResponseEntity<?> createOrganization(OrganizationRequestDTO dto);
    ResponseEntity<?> getOrganizationById(UUID id);
    ResponseEntity<?> getAllOrganizations();
    ResponseEntity<?> approveOrganization(UUID orgId, UUID userId);
//    ResponseEntity<?> getOrgInfo(UUID orgId);
}
