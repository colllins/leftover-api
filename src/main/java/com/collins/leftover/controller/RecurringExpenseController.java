package com.collins.leftover.controller;

import com.collins.leftover.dto.recurringexpense.CreateRecurringExpenseRequestDto;
import com.collins.leftover.dto.recurringexpense.RecurringExpenseResponseDto;
import com.collins.leftover.service.RecurringExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users/recurring-expenses")
@RequiredArgsConstructor
public class RecurringExpenseController {

    private final RecurringExpenseService recurringExpenseService;

    @PostMapping
    public RecurringExpenseResponseDto createRecurringExpense(
            @Valid @RequestBody CreateRecurringExpenseRequestDto dto) {

        String email = getLoggedInUserEmail();
        return recurringExpenseService.createRecurringExpense(email, dto);
    }

    @GetMapping
    public List<RecurringExpenseResponseDto> getUserRecurringExpenses() {
        String email = getLoggedInUserEmail();
        return recurringExpenseService.getActiveRecurringExpenses(email);
    }

    @GetMapping("/{expenseId}")
    public RecurringExpenseResponseDto getRecurringExpenseById(
            @PathVariable("expenseId") @Positive Long recurringExpenseId) {

        String email = getLoggedInUserEmail();
        return recurringExpenseService.getRecurringExpenseById(email, recurringExpenseId);
    }

    @PatchMapping("/{expenseId}/deactivate")
    public void deactivateRecurringExpense(
            @PathVariable("expenseId") @Positive Long expenseId) {

        String email = getLoggedInUserEmail();
        recurringExpenseService.deactivateRecurringExpense(email, expenseId);
    }

    @DeleteMapping("/{expenseId}")
    public void deleteRecurringExpense(
            @PathVariable("expenseId") @Positive Long expenseId) {

        String email = getLoggedInUserEmail();
        recurringExpenseService.deleteRecurringExpense(email, expenseId);
    }

    private String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}