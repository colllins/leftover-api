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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPeriodService {

    private final PayPeriodRepository payPeriodRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public PayPeriodService(PayPeriodRepository payPeriodRepository, UserRepository userRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
        this.payPeriodRepository = payPeriodRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public PayPeriodResponseDto createPayPeriod(Long userId, CreatePayPeriodRequestDto createPayPeriodRequestDto){
        //check if user exists
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!"));

        //create the pay period on this specific user
        PayPeriod payPeriod = new PayPeriod(user, createPayPeriodRequestDto.getStartDate(), createPayPeriodRequestDto.getEndDate(), createPayPeriodRequestDto.getPlannedIncome(), true);
        payPeriodRepository.findAll().forEach(period -> {
            period.setActive(false);
        });
        payPeriodRepository.save(payPeriod);

        return new PayPeriodResponseDto(payPeriod.getId(), payPeriod.getStartDate(), payPeriod.getEndDate(), payPeriod.getPlannedIncome(), payPeriod.isActive());
    }

    @Transactional
    public List<PayPeriodResponseDto> getPayPeriodsForUser(Long userId){
        List<PayPeriodResponseDto> payPeriods = new ArrayList<>();

        //check if user exists
        if(!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!");

        //fetch all payPeriods and foreach payPeriod check if that payPeriod userId equals userId passed in the header and add to the list
        payPeriodRepository.findAllByUser_Id(userId).forEach(payPeriod -> {
                PayPeriodResponseDto periodResponseDto = new PayPeriodResponseDto(payPeriod.getId(), payPeriod.getStartDate(), payPeriod.getEndDate(), payPeriod.getPlannedIncome(), payPeriod.isActive());
                payPeriods.add(periodResponseDto);
        });

        return payPeriods;
    }

    @Transactional
    public  PayPeriodResponseDto getPayPeriodById(Long userId, Long payPeriodId){
        //check if user exists
        if(!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!");

        PayPeriod payPeriod = payPeriodRepository.findByIdAndUser_Id(payPeriodId, userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PayPeriod with that id Not Found!"));

        return new PayPeriodResponseDto(payPeriod.getId(), payPeriod.getStartDate(), payPeriod.getEndDate(), payPeriod.getPlannedIncome(), payPeriod.isActive());
    }

    @Transactional
    public DashboardSummaryResponseDto getPayPeriodSummary(Long userId, Long payPeriodId, int limit){
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        BigDecimal leftOver;

        List<Transaction> transactions = transactionRepository.findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(userId, payPeriodId);

        if(!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                if (transaction.getType() == TransactionType.INCOME) {
                    income = income.add(transaction.getAmount());
                } else if (transaction.getType() == TransactionType.EXPENSE) {
                    expense = expense.add(transaction.getAmount());
                }
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions for this pay period");
        }
            leftOver = income.subtract(expense);

            PayPeriodResponseDto payPeriod = getPayPeriodById(userId, payPeriodId);

       return new DashboardSummaryResponseDto(payPeriodId, payPeriod.getStartDate(), payPeriod.getEndDate(),payPeriod.getPlannedIncome(), income, expense, leftOver, transactionService.getRecentTransactions(userId, limit));
    }

}
