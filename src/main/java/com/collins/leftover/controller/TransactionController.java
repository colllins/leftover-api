package com.collins.leftover.controller;

import com.collins.leftover.dto.transaction.CreateTransactionRequestDto;
import com.collins.leftover.dto.transaction.TransactionResponseDto;
import com.collins.leftover.dto.transaction.UpdateTransactionDto;
import com.collins.leftover.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/users/{userId}/transactions")
    public TransactionResponseDto createTransaction(@PathVariable @Positive Long userId, @Valid @RequestBody CreateTransactionRequestDto dto){
        return transactionService.createTransaction(userId, dto);
    }

    @GetMapping("/api/users/{userId}/pay-period/{payPeriodId}/transactions")
    public List<TransactionResponseDto> getAllTransactionsFroPayPeriod(@PathVariable @Positive Long userId, @PathVariable @Positive Long payPeriodId){
        return transactionService.getTransactionsForPayPeriod(userId, payPeriodId);
    }

    @GetMapping("/api/users/{userId}/transactions/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable @Positive Long userId, @PathVariable @Positive Long transactionId){
        return transactionService.getTransactionById(userId, transactionId);
    }

    @PatchMapping("/api/users/{userId}/transactions/{transaction}")
    public TransactionResponseDto updateTransaction(@PathVariable @Positive Long userId, @PathVariable @Positive Long transactionId, @Valid @RequestBody UpdateTransactionDto dto){
        return transactionService.updateTransaction(userId, transactionId, dto);
    }

    @DeleteMapping("/api/users/{userId}/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable @Positive Long userId, @PathVariable @Positive Long transactionId){
         transactionService.deleteTransaction(userId, transactionId);
    }
}
