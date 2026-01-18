package com.collins.leftover.dto.recurringexpense;

import com.collins.leftover.model.RecurringType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecurringExpenseResponseDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private RecurringType recurringType;
    private boolean active;
    private LocalDate createdAt;
    private LocalDate updatedAt;


    public RecurringExpenseResponseDto() {}

    public RecurringExpenseResponseDto(Long id, String name, BigDecimal amount, RecurringType recurringType, boolean active, LocalDate createdAt, LocalDate updatedAt ) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.recurringType = recurringType;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RecurringType getRecurringType() {
        return recurringType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return active;
    }
}
