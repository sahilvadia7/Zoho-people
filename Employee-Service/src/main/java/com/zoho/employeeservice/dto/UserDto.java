package com.zoho.employeeservice.dto;

import com.zoho.employeeservice.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private String name;
    private String email;
    private Role role;
    private UUID organizationId;
    private String password;
}
