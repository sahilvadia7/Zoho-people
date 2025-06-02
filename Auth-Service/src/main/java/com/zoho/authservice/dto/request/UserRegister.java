package com.zoho.authservice.dto.request;

import com.zoho.authservice.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRegister {
    private String name;
    private String email;
    private Role role;
    private UUID organizationId;
    private String password;
}
