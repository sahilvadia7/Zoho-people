package com.zoho.payrollservice.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollResponse {
    private UUID payrollId;
    private UUID employeeId;
    private BigDecimal salary;
    private BigDecimal bonus;
    private BigDecimal expensePay;
    private BigDecimal tax;
    private BigDecimal totalInHandSalary;
    private Date createdAt;
    private Date updatedAt;
}