package com.zoho.payrollservice.dto.request;

import com.zoho.payrollservice.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExpenseRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotBlank(message = "Expense type is required")
    private String expenseType;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Pending|Approved", message = "Status must be 'Pending' or 'Approved'")
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
