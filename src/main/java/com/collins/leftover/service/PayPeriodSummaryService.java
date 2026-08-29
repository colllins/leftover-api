package com.collins.leftover.service;

import com.collins.leftover.dto.payperiod.PayPeriodSummaryResponseDto;
import com.collins.leftover.dto.transaction.TransactionResponseDto;
import com.collins.leftover.model.*;
import com.collins.leftover.repository.*;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPeriodSummaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPeriodSummaryService.class);

    private final TransactionRepository transactionRepository;
    private final PayPeriodSummaryRepository payPeriodSummaryRepository;
    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserRepository userRepository;
    private final PayPeriodRepository payPeriodRepository;

   // @CachePut(value = "payPeriodSummaries", key = "#payPeriod.user.email + ':' + #payPeriod.id")
    public PayPeriodSummary createSummary(PayPeriod payPeriod) {

        BigDecimal income = payPeriod.getPlannedIncome();

        BigDecimal transactionIncome = transactionRepository
                .findAllByPayPeriod_Id(payPeriod.getId())
                .stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        income = income.add(transactionIncome);

        BigDecimal transactionExpenses = transactionRepository
                .findAllByPayPeriod_Id(payPeriod.getId())
                .stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal recurringExpenses = recurringExpenseRepository
                .findAllByUser_Id(payPeriod.getUser().getId())
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

    @Cacheable(value = "payPeriodSummaries", key = "#email + ':' + #payPeriodId")
    public PayPeriodSummaryResponseDto getPayPeriodSummary(String email, Long payPeriodId) {

        LOGGER.info("Fetching pay period summary from database for payPeriodId: {}", payPeriodId);

        User user = getUserByEmail(email);

        PayPeriod payPeriod = payPeriodRepository.findByIdAndUser_Id(payPeriodId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pay period not found for this user"
                ));

        PayPeriodSummary summary = payPeriodSummaryRepository
                .findByPayPeriod_IdAndUser_Email(payPeriodId, email)
                .orElseGet(() -> createSummary(payPeriod));

        List<TransactionResponseDto> transactions = transactionRepository
                .findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(user.getId(), payPeriodId)
                .stream()
                .map(transaction -> new TransactionResponseDto(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getCategory(),
                        transaction.getDate(),
                        transaction.getDescription()
                ))
                .toList();

        return new PayPeriodSummaryResponseDto(
                summary.getId(),
                payPeriod.getStartDate(),
                payPeriod.getEndDate(),
                summary.getIncome(),
                summary.getExpenses(),
                summary.getLeftOver(),
                transactions
        );
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with that email not found!"
                ));
    }
}