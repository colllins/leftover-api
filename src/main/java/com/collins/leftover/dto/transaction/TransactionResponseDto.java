package com.collins.leftover.dto.transaction;

import com.collins.leftover.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponseDto {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String description;

    public TransactionResponseDto() {}

    public TransactionResponseDto(Long id, TransactionType type, BigDecimal amount, String category, LocalDate date, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
