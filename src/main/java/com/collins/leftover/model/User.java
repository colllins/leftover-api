package com.collins.leftover.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * User entity represents a Leftover user.
 * It stores identity, onboarding, pay frequency, and simple audit timestamps.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private PayFrequency payFrequency;
    private LocalDate onBoardingDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(){}

    public User(String name, String email, PayFrequency payFrequency, LocalDate onBoardingDate) {
        this.name = name;
        this.email = email;
        this.payFrequency = payFrequency;
        this.onBoardingDate = onBoardingDate;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PayFrequency getPayFrequency() {
        return payFrequency;
    }

    public void setPayFrequency(PayFrequency payFrequency) {
        this.payFrequency = payFrequency;
    }

    public LocalDate getOnBoardingDate() {
        return onBoardingDate;
    }

    public void setOnBoardingDate(LocalDate onBoardingDate) {
        this.onBoardingDate = onBoardingDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", payFrequency=" + payFrequency +
                ", onBoardingDate=" + onBoardingDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
