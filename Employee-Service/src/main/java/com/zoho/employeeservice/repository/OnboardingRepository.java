package com.zoho.employeeservice.repository;

import com.zoho.employeeservice.dto.request.HiringRequest;
import com.zoho.employeeservice.model.HiringOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OnboardingRepository extends JpaRepository<HiringOnboarding, UUID> {
    Optional<HiringOnboarding> findByEmployeeId(UUID employeeId);
}
