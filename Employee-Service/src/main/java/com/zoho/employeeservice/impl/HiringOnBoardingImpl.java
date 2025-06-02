package com.zoho.employeeservice.impl;


import com.zoho.employeeservice.dto.request.HiringRequest;
import com.zoho.employeeservice.enums.Status;
import com.zoho.employeeservice.model.HiringOnboarding;
import com.zoho.employeeservice.repository.OnboardingRepository;
import com.zoho.employeeservice.service.HiringOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HiringOnBoardingImpl implements HiringOnboardingService {

    private final OnboardingRepository onboardingRepository;

    @Override
    public ResponseEntity<?> startOnBoarding(HiringRequest onboarding) {
        HiringOnboarding onboard = HiringOnboarding.builder()
                .employeeId(onboarding.getEmployeeId())
                .status(Status.PENDING)
                .startDate(onboarding.getStartDate())
                .endDate(onboarding.getEndDate())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();

        return new ResponseEntity<>(onboardingRepository.save(onboard), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOnboarding(UUID employeeId) {
        Optional<?> onboarding = onboardingRepository.findById(employeeId);
        return new ResponseEntity<>(onboarding,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> completeOnBoarding(UUID employeeId) {
        Optional<HiringOnboarding> onboardingOpt = onboardingRepository.findByEmployeeId(employeeId);
        if (onboardingOpt.isPresent()) {
            HiringOnboarding onboarding = onboardingOpt.get();
            onboarding.setStatus(Status.ACCEPTED);
            onboarding.setEndDate(new Date());
            onboarding.setUpdatedAt(new Date());
            HiringOnboarding updatedOnboarding = onboardingRepository.save(onboarding);
            return new ResponseEntity<>(updatedOnboarding,HttpStatus.OK);
        }
            return new ResponseEntity<>("Onboarding record not found", HttpStatus.NOT_FOUND);

    }
}
