package com.collins.leftover.dto.payperiod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreatePayPeriodRequestDto {

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal plannedIncome;

    public CreatePayPeriodRequestDto(){}

    public CreatePayPeriodRequestDto(LocalDate startDate, LocalDate endDate, BigDecimal plannedIncome) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedIncome = plannedIncome;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPlannedIncome(BigDecimal plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPlannedIncome() {
        return plannedIncome;
    }
}
