package com.collins.leftover.service;

import com.collins.leftover.dto.dashboard.DashboardSummaryResponseDto;
import com.collins.leftover.dto.payperiod.CreatePayPeriodRequestDto;
import com.collins.leftover.dto.payperiod.PayPeriodResponseDto;
import com.collins.leftover.model.PayPeriod;
import com.collins.leftover.model.Transaction;
import com.collins.leftover.model.TransactionType;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.PayPeriodRepository;
import com.collins.leftover.repository.TransactionRepository;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPeriodService {

    private final PayPeriodRepository payPeriodRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public PayPeriodResponseDto createPayPeriod(String email, CreatePayPeriodRequestDto createPayPeriodRequestDto) {
        User user = getUserByEmail(email);

        payPeriodRepository.findAllByUser_Id(user.getId()).forEach(period -> {
            period.setActive(false);
        });

        PayPeriod payPeriod = new PayPeriod(
                user,
                createPayPeriodRequestDto.getStartDate(),
                createPayPeriodRequestDto.getEndDate(),
                createPayPeriodRequestDto.getPlannedIncome(),
                true
        );

        payPeriodRepository.save(payPeriod);

        return mapToPayPeriodResponseDto(payPeriod);
    }

    public List<PayPeriodResponseDto> getPayPeriodsForUser(String email) {
        User user = getUserByEmail(email);

        List<PayPeriodResponseDto> payPeriods = new ArrayList<>();

        payPeriodRepository.findAllByUser_Id(user.getId()).forEach(payPeriod -> {
            payPeriods.add(mapToPayPeriodResponseDto(payPeriod));
        });

        return payPeriods;
    }

    public PayPeriodResponseDto getPayPeriodById(String email, Long payPeriodId) {
        User user = getUserByEmail(email);

        PayPeriod payPeriod = payPeriodRepository.findByIdAndUser_Id(payPeriodId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pay period with that id not found for this user!"
                ));

        return mapToPayPeriodResponseDto(payPeriod);
    }

    public DashboardSummaryResponseDto getPayPeriodSummary(String email, Long payPeriodId, int limit) {
        User user = getUserByEmail(email);

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        List<Transaction> transactions =
                transactionRepository.findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(user.getId(), payPeriodId);

        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions for this pay period");
        }

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                income = income.add(transaction.getAmount());
            } else if (transaction.getType() == TransactionType.EXPENSE) {
                expense = expense.add(transaction.getAmount());
            }
        }

        PayPeriodResponseDto payPeriod = getPayPeriodById(email, payPeriodId);

        income = income.add(payPeriod.getPlannedIncome());

        BigDecimal leftOver = income.subtract(expense);

        return new DashboardSummaryResponseDto(
                payPeriodId,
                payPeriod.getStartDate(),
                payPeriod.getEndDate(),
                payPeriod.getPlannedIncome(),
                income,
                expense,
                leftOver,
                transactionService.getRecentTransactions(user.getId(), limit)
        );
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with that email not found!"
                ));
    }

    private PayPeriodResponseDto mapToPayPeriodResponseDto(PayPeriod payPeriod) {
        return new PayPeriodResponseDto(
                payPeriod.getId(),
                payPeriod.getStartDate(),
                payPeriod.getEndDate(),
                payPeriod.getPlannedIncome(),
                payPeriod.isActive()
        );
    }
}