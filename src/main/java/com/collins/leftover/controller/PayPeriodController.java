package com.collins.leftover.controller;

import com.collins.leftover.dto.payperiod.CreatePayPeriodRequestDto;
import com.collins.leftover.dto.payperiod.PayPeriodResponseDto;
import com.collins.leftover.dto.payperiod.PayPeriodSummaryResponseDto;
import com.collins.leftover.service.PayPeriodService;
import com.collins.leftover.service.PayPeriodSummaryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users/pay-periods")
@RequiredArgsConstructor
public class PayPeriodController {

    private final PayPeriodSummaryService payPeriodSummaryService;
    private final PayPeriodService payPeriodService;

    @PostMapping
    public PayPeriodResponseDto createPayPeriod(
            @Valid @RequestBody CreatePayPeriodRequestDto createPayPeriodRequestDto) {

        String email = getLoggedInUserEmail();
        return payPeriodService.createPayPeriod(email, createPayPeriodRequestDto);
    }

    @GetMapping
    public List<PayPeriodResponseDto> getUserPayPeriods() {
        String email = getLoggedInUserEmail();
        return payPeriodService.getPayPeriodsForUser(email);
    }

    @GetMapping("/{payPeriodId}")
    public PayPeriodResponseDto getPayPeriodById(
            @PathVariable @Positive Long payPeriodId) {

        String email = getLoggedInUserEmail();
        return payPeriodService.getPayPeriodById(email, payPeriodId);
    }

    @GetMapping("/{payPeriodId}/summary")
    public PayPeriodSummaryResponseDto getPayPeriodSummary(
            @PathVariable @Positive Long payPeriodId) {

        String email = getLoggedInUserEmail();
        return payPeriodSummaryService.getPayPeriodSummary(email, payPeriodId);
    }

    private String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}