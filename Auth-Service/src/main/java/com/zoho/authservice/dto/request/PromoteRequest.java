package com.zoho.authservice.dto.request;

import com.zoho.authservice.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class PromoteRequest {
    private UUID userId;
    private Role newRole;
}
