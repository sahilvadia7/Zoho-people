package com.zoho.payrollservice.impl;

import com.zoho.payrollservice.dto.UserDto;
import com.zoho.payrollservice.dto.request.ExpenseRequest;
import com.zoho.payrollservice.enums.Role;
import com.zoho.payrollservice.enums.Status;
import com.zoho.payrollservice.feign.EmployeeClient;
import com.zoho.payrollservice.model.Expense;
import com.zoho.payrollservice.repository.ExpenseRepository;
import com.zoho.payrollservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zoho.payrollservice.constrain.GlobalConstrain.*;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EmployeeClient employeeClient;

    @Override
    public ResponseEntity<?> addExpense(ExpenseRequest request) {

        Expense expense = Expense.builder()
                .employeeId(request.getEmployeeId())
                .amount(request.getAmount())
                .createdAt(new Date())
                .status(Status.PENDING)
                .expenseType(request.getExpenseType())
                .updatedAt(new Date())
                .build();

        return new ResponseEntity<>(expenseRepository.save(expense), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateExpense(ExpenseRequest request, UUID expenseId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);

        if(optionalExpense.isPresent()){
            Expense existingExpense = optionalExpense.get();

            if(request.getEmployeeId() != null){
                existingExpense.setEmployeeId(request.getEmployeeId());
            }
            if(request.getExpenseType() != null){
                existingExpense.setExpenseType(request.getExpenseType());
            }
            if(request.getAmount() != null){
                existingExpense.setAmount(request.getAmount());
            }

            existingExpense.setUpdatedAt(new Date());

            Expense updateExpense = expenseRepository.save(existingExpense);
            return new ResponseEntity<>(updateExpense,HttpStatus.OK);
        }
        return new ResponseEntity<>(EXPENSE_NOT_FOUND,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteExpense(UUID expenseId) {

        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if(optionalExpense.isPresent()){
            expenseRepository.deleteById(expenseId);
            return new ResponseEntity<>(EXPENSE_DELETED,HttpStatus.OK);
        }
        return new ResponseEntity<>(EXPENSE_NOT_FOUND,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> expenseByEmployeeId(UUID id) {
        Optional<List<Expense>> optionalExpenseByEmployee = expenseRepository.findByEmployeeId(id);

        if(optionalExpenseByEmployee.isEmpty()){
            return new ResponseEntity<>(NO_EXPENSE_FOUND,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalExpenseByEmployee,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> approveExpense(@RequestParam UUID expenseId,@RequestParam Status status, UUID id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        System.out.println("Expense : "+optionalExpense);
        if(optionalExpense.isPresent()){

            ResponseEntity<UserDto> isItHr = employeeClient.getEmployee(id);

            if(isItHr!=null && isItHr.getBody().getRole().equals(Role.HR)){

            Expense approve = optionalExpense.get();
            approve.setStatus(status);
            approve.setUpdatedAt(new Date());
            expenseRepository.save(approve);

            return new ResponseEntity<>(EXPENSE_APPROVED,HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(NO_EXPENSE_FOUND,HttpStatus.OK);

    }
}
