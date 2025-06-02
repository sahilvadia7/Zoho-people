package com.zoho.employeeservice.service;

import com.zoho.employeeservice.dto.request.ShiftRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ShiftService {

    ResponseEntity<?> addShift(ShiftRequest shiftRequest);
    ResponseEntity<?> assignShift(UUID empUuid,UUID shiftId);
    ResponseEntity<?> updateShift(UUID shiftId,ShiftRequest shiftRequest);
}
