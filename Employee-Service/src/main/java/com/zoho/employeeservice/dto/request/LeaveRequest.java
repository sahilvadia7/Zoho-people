package com.zoho.employeeservice.dto.request;

import com.zoho.employeeservice.enums.Leavee;
import com.zoho.employeeservice.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;


@Data
@Builder
public class LeaveRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotNull(message = "Leave type is required")
    private Leavee leaveType;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotNull(message = "Status is required")
    private Status status;

    private Date createdAt;
    private Date updatedAt;
}
