package com.collins.leftover.dto.payperiod;

import com.collins.leftover.dto.transaction.TransactionResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PayPeriodSummaryResponseDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private  BigDecimal leftOver;
    private List<TransactionResponseDto> transactions;

    public PayPeriodSummaryResponseDto(){}

    public PayPeriodSummaryResponseDto(Long id, LocalDate startDate, LocalDate endDate, BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal leftOver, List<TransactionResponseDto> transactions) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.leftOver = leftOver;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getLeftOver() {
        return leftOver;
    }

    public List<TransactionResponseDto> getTransactions() {
        return transactions;
    }
}
