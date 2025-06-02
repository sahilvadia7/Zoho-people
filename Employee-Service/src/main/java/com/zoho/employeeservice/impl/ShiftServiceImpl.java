package com.zoho.employeeservice.impl;

import com.zoho.employeeservice.dto.request.ShiftRequest;
import com.zoho.employeeservice.model.Employee;
import com.zoho.employeeservice.model.Shift;
import com.zoho.employeeservice.repository.EmployeeRepository;
import com.zoho.employeeservice.repository.ShiftRepository;
import com.zoho.employeeservice.service.ShiftService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.zoho.employeeservice.constrain.GlobalConstrain.*;


@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;


    @Override
    public ResponseEntity<?> addShift(ShiftRequest shiftRequest) {
        Shift shift = Shift.builder()
                .shiftName(shiftRequest.getShiftName())
                .startTime(shiftRequest.getStartTime())
                .endTime(shiftRequest.getEndTime())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        return new ResponseEntity<>(shiftRepository.save(shift), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> assignShift(UUID empUuid,UUID shiftId){

        Optional<Employee> userExist = employeeRepository.findById(empUuid);
        if(userExist.isPresent()){
            Employee userEmployee = userExist.get();
            userEmployee.setShiftId(shiftId);

            return new ResponseEntity<>(employeeRepository.save(userEmployee),HttpStatus.OK);
        }

    return new ResponseEntity<>(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateShift(UUID shiftId, ShiftRequest shiftRequest) {
        Optional<Shift> optionalShift = shiftRepository.findById(shiftId);

        if (optionalShift.isPresent()) {
            Shift existingShift = optionalShift.get();

            if (shiftRequest.getShiftName() != null) {
                existingShift.setShiftName(shiftRequest.getShiftName());
            }
            if (shiftRequest.getStartTime() != null) {
                existingShift.setStartTime(shiftRequest.getStartTime());
            }
            if (shiftRequest.getEndTime() != null) {
                existingShift.setEndTime(shiftRequest.getEndTime());
            }
            existingShift.setUpdatedAt(new Date());

            Shift updatedShift = shiftRepository.save(existingShift);
            return new ResponseEntity<>(updatedShift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(SHIFT_NOT_FOUND_MESSAGE + shiftId, HttpStatus.NOT_FOUND);
        }
    }
}
