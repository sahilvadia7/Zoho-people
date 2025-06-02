package com.zoho.employeeservice.dto.request;

import com.zoho.employeeservice.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class HiringRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    private Date createdAt;
    private Date updatedAt;
}