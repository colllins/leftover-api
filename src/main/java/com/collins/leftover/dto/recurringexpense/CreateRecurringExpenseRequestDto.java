package com.collins.leftover.dto.recurringexpense;

import com.collins.leftover.model.RecurringType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateRecurringExpenseRequestDto {
    @NotBlank
    @Size(max = 60)
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal amount;

    @NotNull
    private RecurringType recurringType;

    public CreateRecurringExpenseRequestDto() {}

    public CreateRecurringExpenseRequestDto(String name, BigDecimal amount, RecurringType recurringType) {
        this.name = name;
        this.amount = amount;
        this.recurringType = recurringType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
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
}
