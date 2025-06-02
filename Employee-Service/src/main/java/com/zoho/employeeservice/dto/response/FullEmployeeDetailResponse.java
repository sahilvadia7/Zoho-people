package com.zoho.employeeservice.dto.response;

import com.zoho.employeeservice.enums.Role;
import com.zoho.employeeservice.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class FullEmployeeDetailResponse {
    private String name;
    private String email;
    private Role role;
    private UUID organizationId;
    private String designation;
    private String department;
    private BigInteger phone;
    private Date dateOfJoining;
    private String employeeCode;
}
