package com.zoho.payrollservice.repository;

import com.zoho.payrollservice.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayrollRepository extends JpaRepository<Payroll, UUID> {
}
