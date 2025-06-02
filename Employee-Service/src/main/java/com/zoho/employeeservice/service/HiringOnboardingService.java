package com.zoho.employeeservice.service;

import com.zoho.employeeservice.dto.request.HiringRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface HiringOnboardingService {

    ResponseEntity<?> startOnBoarding(HiringRequest onboarding);
    ResponseEntity<?> getOnboarding(UUID employeeId);
    ResponseEntity<?> completeOnBoarding(UUID employeeId);
}
