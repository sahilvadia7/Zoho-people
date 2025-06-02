package com.zoho.payrollservice.model;

import com.zoho.payrollservice.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Expense {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID employeeId;
    private String expenseType;
    private BigDecimal amount;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
}
