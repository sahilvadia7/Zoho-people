package com.zoho.employeeservice.model;

import com.zoho.employeeservice.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Onboarding_process")
public class HiringOnboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID employeeId;
    private Status status;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
}