package com.zoho.organizationservice.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class LicenseRequest {
    private UUID organizationId;
    private int requestedCount;
}