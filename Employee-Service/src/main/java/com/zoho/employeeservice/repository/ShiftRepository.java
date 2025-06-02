package com.zoho.employeeservice.repository;

import com.zoho.employeeservice.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {
}
