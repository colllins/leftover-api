package com.collins.leftover.service;

import com.collins.leftover.model.*;
import com.collins.leftover.repository.PayPeriodSummaryRepository;
import com.collins.leftover.repository.RecurringExpenseRepository;
import com.collins.leftover.repository.TransactionRepository;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPeriodSummaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPeriodSummaryService.class);

    private final TransactionRepository transactionRepository;
    private final PayPeriodSummaryRepository payPeriodSummaryRepository;
    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserRepository userRepository;

    @CachePut(value = "payPeriodSummaries", key = "#payPeriod.user.email + ':' + #payPeriod.id")
    public PayPeriodSummary createSummary(PayPeriod payPeriod) {

        BigDecimal income = payPeriod.getPlannedIncome();

        BigDecimal transactionIncome = transactionRepository
                .findAllByPayPeriodId(payPeriod.getId())
                .stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        income = income.add(transactionIncome);

        BigDecimal transactionExpenses = transactionRepository
                .findAllByPayPeriodId(payPeriod.getId())
                .stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal recurringExpenses = recurringExpenseRepository.findAllByUser_Id(payPeriod.getUser().getId())
                .stream()
                .filter(RecurringExpense::isActive)
                .map(RecurringExpense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenses = transactionExpenses.add(recurringExpenses);
        BigDecimal leftOver = income.subtract(expenses);

        PayPeriodSummary summary = new PayPeriodSummary();
        summary.setUser(payPeriod.getUser());
        summary.setPayPeriod(payPeriod);
        summary.setIncome(income);
        summary.setExpenses(expenses);
        summary.setRecurringExpenses(recurringExpenses);
        summary.setLeftOver(leftOver);
        summary.setCreatedAt(LocalDateTime.now());

        return payPeriodSummaryRepository.save(summary);
    }

    @Cacheable(value = "payPeriodSummaries", key="#email + ':' + #payPeriodId")
    public PayPeriodSummary getPayPeriodSummary(String email, Long payPeriodId){

        LOGGER.info("Fetching pay period summary from database for payPeriodId: {}", payPeriodId);

        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that email Not Found!"));

        PayPeriodSummary payPeriodSummary = payPeriodSummaryRepository.findByPayPeriodIdAndUserEmail(payPeriodId, email).orElseThrow(()-> new RuntimeException("Pay period doest not exist"));

        return payPeriodSummary;
    }
}
