package com.collins.leftover.dto.transaction;

import com.collins.leftover.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateTransactionDto {
    private TransactionType type;

    @Digits(integer=12, fraction=2)
    @DecimalMin(value="0.01", inclusive=true)
    private BigDecimal amount;

    @Size(max = 50)
    @Pattern(regexp = "^(?!\\s*$).+", message = "category must not be blank")
    private String category;

    @PastOrPresent
    private LocalDate date;

    @Size(max = 280)
    private String description;

    public UpdateTransactionDto() {}

    public UpdateTransactionDto(TransactionType type, BigDecimal amount, String category, LocalDate date, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
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
