package com.collins.leftover.controller;

import com.collins.leftover.dto.dashboard.DashboardSummaryResponseDto;
import com.collins.leftover.dto.payperiod.CreatePayPeriodRequestDto;
import com.collins.leftover.dto.payperiod.PayPeriodResponseDto;
import com.collins.leftover.service.PayPeriodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PayPeriodController {

    private final PayPeriodService payPeriodService;

    public PayPeriodController(PayPeriodService payPeriodService) {
        this.payPeriodService = payPeriodService;
    }

    @PostMapping("/api/users/{userId}/pay-periods")
    public PayPeriodResponseDto createPayPeriod(@PathVariable @Positive Long userId, @Valid @RequestBody CreatePayPeriodRequestDto createPayPeriodRequestDto){
        return payPeriodService.createPayPeriod(userId, createPayPeriodRequestDto);
    }

    @GetMapping("/api/users/{userId}/pay-periods")
    public List<PayPeriodResponseDto> getUserPayPeriods(@PathVariable @Positive Long userId){
        return  payPeriodService.getPayPeriodsForUser(userId);
    }

    @GetMapping("/api/users/{userId}/pay-periods/{payPeriodId}")
    public PayPeriodResponseDto getPayPeriodById(@PathVariable @Positive Long userId, @PathVariable @Positive Long payPeriodId){
        return payPeriodService.getPayPeriodById(userId, payPeriodId);
    }

    @GetMapping("/api/users/{userId}/pay-periods/{payPeriodId}/summary")
    public DashboardSummaryResponseDto getPayPeriodSummary(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long payPeriodId,
            @RequestParam(name="limit", defaultValue = "5") @Positive int limit){
        return payPeriodService.getPayPeriodSummary(userId, payPeriodId, limit);
    }
}
