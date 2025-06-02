package com.zoho.employeeservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ShiftRequest {

    @NotBlank(message = "Shift name is required")
    private String shiftName;

    @NotNull(message = "Shift start time is required")
    private Date startTime;

    @NotNull(message = "Shift end time is required")
    private Date endTime;

    private Date createdAt;
    private Date updatedAt;
}