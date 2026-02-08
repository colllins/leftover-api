package com.collins.leftover.controller;

import com.collins.leftover.dto.recurringexpense.CreateRecurringExpenseRequestDto;
import com.collins.leftover.dto.recurringexpense.RecurringExpenseResponseDto;
import com.collins.leftover.service.RecurringExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class RecurringExpenseController {
    private final RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @PostMapping("/api/users/{userId}/recurring-expenses")
    public RecurringExpenseResponseDto createRecurringExpense(@PathVariable("userId") @Positive Long userId, @Valid @RequestBody CreateRecurringExpenseRequestDto dto){
        return recurringExpenseService.createRecurringExpense(userId, dto);
    }

    @GetMapping("/api/users/{userId}/recurring-expenses")
    public List<RecurringExpenseResponseDto> getUserRecurringExpenses(@PathVariable("userId") @Positive Long userId){
        return recurringExpenseService.getActiveRecurringExpenses(userId);
    }

    @GetMapping("/api/users/{userId}/recurring-expenses/{expenseId}")
    public RecurringExpenseResponseDto getRecurringExpenseById(@PathVariable("userId") @Positive Long userId, @PathVariable("expenseId") @Positive Long recurringExpenseId){
        return recurringExpenseService.getRecurringExpenseById(userId, recurringExpenseId);
    }

    @PatchMapping("/api/users/{userId}/recurring-expenses/{expenseId}/deactivate")
    public void deactivateRecurringExpense(@PathVariable("userId") @Positive Long userId, @PathVariable("expenseId") @Positive Long expenseId){
        recurringExpenseService.deactivateRecurringExpense(userId, expenseId);
    }

    @DeleteMapping("/api/users/{userId}/recurring-expenses/{expenseId}")
    public void deleteRecurringExpense(@PathVariable("userId") @Positive Long userId, @PathVariable("expenseId") @Positive Long expenseId){
        recurringExpenseService.deleteRecurringExpense(userId, expenseId);
    }
}
