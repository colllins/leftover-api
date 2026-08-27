package com.collins.leftover.controller;

import com.collins.leftover.dto.transaction.CreateTransactionRequestDto;
import com.collins.leftover.dto.transaction.TransactionResponseDto;
import com.collins.leftover.dto.transaction.UpdateTransactionDto;
import com.collins.leftover.service.TransactionService;
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
@RequestMapping("/api/users/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponseDto createTransaction(
            @Valid @RequestBody CreateTransactionRequestDto dto) {

        String email = getLoggedInUserEmail();
        return transactionService.createTransaction(email, dto);
    }

    @GetMapping("/pay-periods/{payPeriodId}")
    public List<TransactionResponseDto> getAllTransactionsForPayPeriod(
            @PathVariable("payPeriodId") @Positive Long payPeriodId) {

        String email = getLoggedInUserEmail();
        return transactionService.getTransactionsForPayPeriod(email, payPeriodId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(
            @PathVariable("transactionId") @Positive Long transactionId) {

        String email = getLoggedInUserEmail();
        return transactionService.getTransactionById(email, transactionId);
    }

    @PatchMapping("/{transactionId}")
    public TransactionResponseDto updateTransaction(
            @PathVariable("transactionId") @Positive Long transactionId,
            @Valid @RequestBody UpdateTransactionDto dto) {

        String email = getLoggedInUserEmail();
        return transactionService.updateTransaction(email, transactionId, dto);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(
            @PathVariable("transactionId") @Positive Long transactionId) {

        String email = getLoggedInUserEmail();
        transactionService.deleteTransaction(email, transactionId);
    }

    private String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}