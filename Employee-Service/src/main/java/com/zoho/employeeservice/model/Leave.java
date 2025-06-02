package com.zoho.employeeservice.model;

import com.zoho.employeeservice.enums.Leavee;
import com.zoho.employeeservice.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID employeeId;

    @Enumerated(EnumType.STRING)
    private Leavee leaveType;
    private Date startDate;
    private Date endDate;
    private Status status; // Pending, Approved, Rejected
    private Date createdAt;
    private Date updatedAt;
}