package com.zoho.organizationservice.impl;

import com.zoho.organizationservice.dto.request.LicenseRequest;
import com.zoho.organizationservice.model.License;
import com.zoho.organizationservice.model.Organization;
import com.zoho.organizationservice.repository.LicenseRepository;
import com.zoho.organizationservice.repository.OrganizationRepository;
import com.zoho.organizationservice.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public ResponseEntity<?> requestLicense(@RequestBody LicenseRequest licenseReq) {
        Optional<Organization> orgOpt = organizationRepository.findById(licenseReq.getOrganizationId());
        if (orgOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
        }

        License license = License.builder()
                .organization(orgOpt.get())
                .requestedCount(licenseReq.getRequestedCount())
                .requestedAt(new Date())
                .build();

        License savedLicense = licenseRepository.save(license);
        return ResponseEntity.ok(savedLicense);
    }

    @Override
    public ResponseEntity<?> findByLicId(UUID id) {
        Optional<License> li = licenseRepository.findById(id);
        return new ResponseEntity<>(li.isPresent(),HttpStatus.OK);
    }

}