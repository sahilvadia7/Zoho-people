package com.zoho.organizationservice.controller;

import com.zoho.organizationservice.dto.request.LicenseRequest;
import com.zoho.organizationservice.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping
    public ResponseEntity<?> reqLicense(@RequestBody LicenseRequest licenseRequest){
        return licenseService.requestLicense(licenseRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByLicId(@RequestParam UUID id){
        return licenseService.findByLicId(id);
    }
}
