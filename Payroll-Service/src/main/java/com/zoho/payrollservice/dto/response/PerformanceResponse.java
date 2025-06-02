package com.zoho.payrollservice.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PerformanceResponse {
    private UUID employeeId;
    private Double rating;
    private String feedback;
    private Date createdAt;
    private Date updatedAt;
}
