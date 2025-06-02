package com.zoho.employeeservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
public class EmployeeRequest {

    private UUID employeeId;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Phone number must be 10 digits")
    private BigInteger phone;

    @NotNull(message = "Date of joining is required")
    @PastOrPresent(message = "Date of joining cannot be in the future")
    private Date dateOfJoining;

    @NotBlank(message = "Employee code is required")
    private String employeeCode;

    @NotNull(message = "Shift ID is required")
    private UUID shiftId;

    @NotNull(message = "Leave count is required")
    @Min(value = 0, message = "Leave count cannot be negative")
    private Integer leaveCount;
}