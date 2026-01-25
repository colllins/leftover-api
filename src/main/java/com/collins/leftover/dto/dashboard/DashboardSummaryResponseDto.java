package com.collins.leftover.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DashboardSummaryResponseDto {
    private Long currentPayPeriodId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal plannedIncome;
    private  BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal leftOver;
//    private List<RecurringExpenseResponseDto> recurringExpenses;
//    private List<TransactionResponseDto> recentTransactions;

    public DashboardSummaryResponseDto() {}

    public DashboardSummaryResponseDto(Long currentPayPeriodId, LocalDate startDate, LocalDate endDate, BigDecimal plannedIncome, BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal leftOver) {
        this.currentPayPeriodId = currentPayPeriodId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedIncome = plannedIncome;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.leftOver = leftOver;
//        this.recurringExpenses = recurringExpenses;
//        this.recentTransactions = recentTransactions;
    }

    public Long getCurrentPayPeriodId() {
        return currentPayPeriodId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPlannedIncome() {
        return plannedIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getLeftOver() {
        return leftOver;
    }

//    public List<RecurringExpenseResponseDto> getRecurringExpenses() {
//        return recurringExpenses;
//    }
//
//    public List<TransactionResponseDto> getRecentTransactions() {
//        return recentTransactions;
//    }
}
