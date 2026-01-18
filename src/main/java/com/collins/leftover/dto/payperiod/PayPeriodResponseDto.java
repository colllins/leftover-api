package com.collins.leftover.dto.payperiod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayPeriodResponseDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal plannedIncome;
    private boolean active;

    public PayPeriodResponseDto(){}

    public PayPeriodResponseDto(Long id, LocalDate startDate, LocalDate endDate, BigDecimal plannedIncome, Boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedIncome = plannedIncome;
        this.active = active;
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

    public BigDecimal getPlannedIncome() {
        return plannedIncome;
    }

    public Boolean isActive() {
        return active;
    }
}
