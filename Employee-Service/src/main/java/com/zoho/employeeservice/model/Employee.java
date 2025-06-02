package com.zoho.employeeservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private UUID employeeId;
    private String designation;
    private String department;
    private BigInteger phone;
    private Date dateOfJoining;
    private String employeeCode;
    private UUID shiftId;
    private Integer leaveCount;

}
