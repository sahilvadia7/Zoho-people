package com.zoho.employeeservice.controller;

import com.zoho.employeeservice.dto.request.HiringRequest;
import com.zoho.employeeservice.service.HiringOnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
@Tag(name = "Onboarding API", description = "Handles employee hiring and onboarding process")
public class OnboardingController {

    private final HiringOnboardingService hiringOnboardingService;

    @Operation(
            summary = "Start onboarding process",
            description = "Starts the onboarding process for a newly hired employee"
    )
    @PostMapping("")
    public ResponseEntity<?> startOnboarding(@Valid @RequestBody HiringRequest onboarding) {
        return hiringOnboardingService.startOnBoarding(onboarding);
    }

    @Operation(
            summary = "Get onboarding status",
            description = "Retrieves the onboarding status for a specific employee by ID"
    )
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getOnboarding(@PathVariable UUID employeeId) {
        return hiringOnboardingService.getOnboarding(employeeId);
    }

    @Operation(
            summary = "Complete onboarding",
            description = "Marks the onboarding process as completed for the given employee ID"
    )
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> completeOnboarding(@PathVariable UUID employeeId) {
        return hiringOnboardingService.completeOnBoarding(employeeId);
    }
}
