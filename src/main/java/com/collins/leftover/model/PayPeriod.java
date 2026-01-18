package com.collins.leftover.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class PayPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal plannedIncome;
    private boolean active;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    protected PayPeriod(){}

    public PayPeriod(User user, LocalDate startDate, LocalDate endDate, BigDecimal plannedIncome, boolean active) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedIncome = plannedIncome;
        this.active = active;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;


    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPlannedIncome() {
        return plannedIncome;
    }

    public void setPlannedIncome(BigDecimal plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "PayPeriod{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", plannedIncome=" + plannedIncome +
                ", active=" + active +
                ", createAt=" + createdAt +
                ", updateAt=" + updatedAt +
                '}';
    }
}
