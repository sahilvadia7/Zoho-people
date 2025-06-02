package com.zoho.employeeservice.dto.request;

import com.zoho.employeeservice.model.Task;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class TimesheetRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotEmpty(message = "Task list cannot be empty")
    private List<@NotNull Task> tasks;

    @NotNull(message = "Date is required")
    private Date date;

    @NotNull(message = "Hours worked is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Hours worked must be greater than 0")
    private Double hoursWorked;

    private Date createdAt;
    private Date updatedAt;
}