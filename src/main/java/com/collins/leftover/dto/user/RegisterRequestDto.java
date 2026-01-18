package com.collins.leftover.dto.user;

import com.collins.leftover.model.PayFrequency;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegisterRequestDto {

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @NotNull
    private PayFrequency payFrequency;

    @NotNull
    @PastOrPresent
    private LocalDate onBoardingDate;

    public RegisterRequestDto(){}

    public RegisterRequestDto(String name, String email, PayFrequency payFrequency, LocalDate onBoardingDate) {
        this.name = name;
        this.email = email;
        this.payFrequency = payFrequency;
        this.onBoardingDate = onBoardingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPayFrequency(PayFrequency payFrequency) {
        this.payFrequency = payFrequency;
    }

    public void setOnBoardingDate(LocalDate onBoardingDate) {
        this.onBoardingDate = onBoardingDate;
    }

    public String getEmail() {
        return email;
    }

    public PayFrequency getPayFrequency() {
        return payFrequency;
    }


    public LocalDate getOnBoardingDate() {
        return onBoardingDate;
    }

}
