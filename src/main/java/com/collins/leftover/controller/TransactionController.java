package com.collins.leftover.controller;

import com.collins.leftover.dto.transaction.CreateTransactionRequestDto;
import com.collins.leftover.dto.transaction.TransactionResponseDto;
import com.collins.leftover.dto.transaction.UpdateTransactionDto;
import com.collins.leftover.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

//    @PostMapping("/api/users/{userId}/transactions")
//    public TransactionResponseDto createTransaction(@PathVariable("userId") @Positive Long userId, @Valid @RequestBody CreateTransactionRequestDto dto){
//        return transactionService.createTransaction(userId, dto);
//    }

//    @GetMapping("/api/users/{userId}/transactions/pay-periods/{payPeriodId}")
//    public List<TransactionResponseDto> getAllTransactionsForPayPeriod(@PathVariable("userId") @Positive Long userId, @PathVariable("payPeriodId") @Positive Long payPeriodId){
//        return transactionService.getTransactionsForPayPeriod(userId, payPeriodId);
//    }

//    @GetMapping("/api/users/{userId}/transactions/{transactionId}")
//    public TransactionResponseDto getTransactionById(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId){
//        return transactionService.getTransactionById(userId, transactionId);
//    }

//    @PatchMapping("/api/users/{userId}/transactions/{transactionId}")
//    public TransactionResponseDto updateTransaction(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId, @Valid @RequestBody UpdateTransactionDto dto){
//        return transactionService.updateTransaction(userId, transactionId, dto);
//    }

//    @DeleteMapping("/api/users/{userId}/transactions/{transactionId}")
//    public void deleteTransaction(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId){
//         transactionService.deleteTransaction(userId, transactionId);
//    }

    //

    @PostMapping
    public TransactionResponseDto createTransaction(@PathVariable("userId") @Positive Long userId, @Valid @RequestBody CreateTransactionRequestDto dto){
        return transactionService.createTransaction(userId, dto);
    }

    @GetMapping("/pay-periods/{payPeriodId}")
    public List<TransactionResponseDto> getAllTransactionsForPayPeriod(@PathVariable("userId") @Positive Long userId, @PathVariable("payPeriodId") @Positive Long payPeriodId){
        return transactionService.getTransactionsForPayPeriod(userId, payPeriodId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId){
        return transactionService.getTransactionById(userId, transactionId);
    }

    @PatchMapping("/{transactionId}")
    public TransactionResponseDto updateTransaction(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId, @Valid @RequestBody UpdateTransactionDto dto){
        return transactionService.updateTransaction(userId, transactionId, dto);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable("userId") @Positive Long userId, @PathVariable("transactionId") @Positive Long transactionId){
        transactionService.deleteTransaction(userId, transactionId);
    }
}
