package com.zoho.payrollservice.impl;

import com.zoho.payrollservice.dto.request.PayrollRequest;
import com.zoho.payrollservice.dto.response.PayrollResponse;
import com.zoho.payrollservice.enums.Status;
import com.zoho.payrollservice.model.Expense;
import com.zoho.payrollservice.model.Payroll;
import com.zoho.payrollservice.repository.ExpenseRepository;
import com.zoho.payrollservice.repository.PayrollRepository;
import com.zoho.payrollservice.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final ExpenseRepository expenseRepository;

        @Override
        public ResponseEntity<?> createPayroll(PayrollRequest request) {

            Optional<List<Expense>> allExpenseByEmployeeId = expenseRepository.findByEmployeeId(request.getEmployeeId());

            BigDecimal totalOfAllAcceptedExpense = BigDecimal.ZERO;

            if (allExpenseByEmployeeId.isPresent()) {
                totalOfAllAcceptedExpense = allExpenseByEmployeeId.get().stream()
                        .filter(expense -> Status.ACCEPTED.equals(expense.getStatus()))
                        .map(Expense::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            Payroll payroll = Payroll.builder()
                    .employeeId(request.getEmployeeId())
                    .salary(request.getSalary())
                    .bonus(request.getBonus())
                    .expensePay(totalOfAllAcceptedExpense)
                    .tax(request.getTax())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();

            Payroll savedPayroll = payrollRepository.save(payroll);

            BigDecimal totalInHandSalary = savedPayroll.getSalary()
                    .add(savedPayroll.getBonus())
                    .add(savedPayroll.getExpensePay())
                    .subtract(savedPayroll.getTax());

            PayrollResponse response = PayrollResponse.builder()
                    .payrollId(savedPayroll.getId())
                    .employeeId(savedPayroll.getEmployeeId())
                    .salary(savedPayroll.getSalary())
                    .bonus(savedPayroll.getBonus())
                    .expensePay(savedPayroll.getExpensePay())
                    .tax(savedPayroll.getTax())
                    .totalInHandSalary(totalInHandSalary)
                    .createdAt(savedPayroll.getCreatedAt())
                    .updatedAt(savedPayroll.getUpdatedAt())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

    @Override
    public ResponseEntity<?> payrollById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updatePayroll(UUID id, PayrollRequest request) {
        return null;
    }
}
