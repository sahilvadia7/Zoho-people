package com.zoho.payrollservice.repository;

import com.zoho.payrollservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Optional<List<Expense>> findByEmployeeId(UUID id);
}
