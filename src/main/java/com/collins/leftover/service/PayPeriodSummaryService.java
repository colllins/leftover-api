package com.collins.leftover.service;

import com.collins.leftover.model.PayPeriod;
import com.collins.leftover.model.PayPeriodSummary;
import com.collins.leftover.model.Transaction;
import com.collins.leftover.repository.PayPeriodSummaryRepository;
import com.collins.leftover.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPeriodSummaryService {

    private final TransactionRepository transactionRepository;
    private final PayPeriodSummaryRepository payPeriodSummaryRepository;

    public PayPeriodSummary createSummary(PayPeriod payPeriod) {

        BigDecimal income = payPeriod.getPlannedIncome();

        BigDecimal expenses = transactionRepository
                .findAllByPayPeriodId(payPeriod.getId())
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal leftOver = income.subtract(expenses);

        PayPeriodSummary summary = new PayPeriodSummary();
        summary.setUser(payPeriod.getUser());
        summary.setPayPeriod(payPeriod);
        summary.setIncome(income);
        summary.setExpenses(expenses);
        summary.setLeftOver(leftOver);
        summary.setCreatedAt(LocalDateTime.now());

        return payPeriodSummaryRepository.save(summary);
    }
}
