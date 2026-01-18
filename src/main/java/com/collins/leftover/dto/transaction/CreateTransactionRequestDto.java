package com.collins.leftover.dto.transaction;

import com.collins.leftover.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionRequestDto {

    @NotNull
    @Positive
    private Long payPeriodId;

    @NotNull
    private TransactionType type;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal amount;

    @NotBlank
    @Size(max = 50)
    private String category;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    @NotBlank
    @Size(max = 250)
    private String description;

    public CreateTransactionRequestDto() {}

    public CreateTransactionRequestDto(Long payPeriodId, TransactionType type, BigDecimal amount, String category, LocalDate date, String description) {
        this.payPeriodId = payPeriodId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Long getPayPeriodId() {
        return payPeriodId;
    }

    public void setPayPeriodId(Long payPeriodId) {
        this.payPeriodId = payPeriodId;
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
