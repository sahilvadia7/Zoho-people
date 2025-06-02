package com.zoho.organizationservice.impl;

import com.zoho.organizationservice.dto.request.OrganizationRequestDTO;
import com.zoho.organizationservice.feign.AuthClient;
import com.zoho.organizationservice.model.Organization;
import com.zoho.organizationservice.repository.OrganizationRepository;
import com.zoho.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AuthClient authClient;


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<?> createOrganization(OrganizationRequestDTO dto) {

        String encodePass = passwordEncoder().encode(dto.getPassword());
        Organization org = Organization.builder()
                .organizationName(dto.getOrganizationName())
                .password(encodePass)
                .email(dto.getEmail())
                .location(dto.getLocation())
                .contactNO(dto.getContactNO())
                .webLink(dto.getWebLink())
                .createdAt(new Date())
                .adminId(dto.getAdminId())
                .authKey(UUID.randomUUID().toString())
                .build();

        organizationRepository.save(org);
        return ResponseEntity.ok(org);
    }

    @Override
    public ResponseEntity<?> getOrganizationById(UUID id) {
        return organizationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getAllOrganizations() {
        return ResponseEntity.ok(organizationRepository.findAll());
    }

    @Override
    public ResponseEntity<?> approveOrganization(UUID orgId, UUID userId) {
        ResponseEntity<?> roleResponse = authClient.getRole(userId);
        if (roleResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }

        String role = (String) roleResponse.getBody();
        if (!"SUPER_ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Super Admin can approve organizations");
        }

        Optional<Organization> orgOpt = organizationRepository.findById(orgId);
        if (orgOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
        }

        Organization org = orgOpt.get();
        org.setApproved(true);
        organizationRepository.save(org);

        return ResponseEntity.ok("Organization approved successfully.");
    }


}
