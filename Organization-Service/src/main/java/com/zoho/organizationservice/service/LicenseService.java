package com.zoho.organizationservice.service;

import com.zoho.organizationservice.dto.request.LicenseRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface LicenseService {
    ResponseEntity<?> requestLicense(LicenseRequest licenseReq);
    ResponseEntity<?> findByLicId(UUID id);
}
