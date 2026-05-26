package com.collins.leftover.controller;

import com.collins.leftover.dto.payperiod.CreatePayPeriodRequestDto;
import com.collins.leftover.dto.payperiod.PayPeriodResponseDto;
import com.collins.leftover.model.PayPeriodSummary;
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

//    @PostMapping("/api/users/{userId}/pay-periods")
//    public PayPeriodResponseDto createPayPeriod(@PathVariable("userId") @Positive Long userId, @Valid @RequestBody CreatePayPeriodRequestDto createPayPeriodRequestDto){
//        return payPeriodService.createPayPeriod(userId, createPayPeriodRequestDto);
//    }
//
//    @GetMapping("/api/users/{userId}/pay-periods")
//    public List<PayPeriodResponseDto> getUserPayPeriods(@PathVariable("userId") @Positive Long userId){
//        return  payPeriodService.getPayPeriodsForUser(userId);
//    }
//
//    @GetMapping("/api/users/{userId}/pay-periods/{payPeriodId}")
//    public PayPeriodResponseDto getPayPeriodById(@PathVariable("userId") @Positive Long userId, @PathVariable("payPeriodId") @Positive Long payPeriodId){
//        return payPeriodService.getPayPeriodById(userId, payPeriodId);
//    }
//
//    @GetMapping("/api/users/{userId}/pay-periods/{payPeriodId}/summary")
//    public DashboardSummaryResponseDto getPayPeriodSummary(
//            @PathVariable("userId") @Positive Long userId,
//            @PathVariable("payPeriodId") @Positive Long payPeriodId,
//            @RequestParam(name="limit", defaultValue = "5") @Positive int limit){
//        return payPeriodService.getPayPeriodSummary(userId, payPeriodId, limit);
//    }

        @PostMapping
        public PayPeriodResponseDto createPayPeriod(@Valid @RequestBody CreatePayPeriodRequestDto createPayPeriodRequestDto){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return payPeriodService.createPayPeriod(email, createPayPeriodRequestDto);
        }

        @GetMapping
        public List<PayPeriodResponseDto> getUserPayPeriods(){
            return  payPeriodService.getPayPeriodsForUser(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        @GetMapping("/{payPeriodId}")
        public PayPeriodResponseDto getPayPeriodById(@PathVariable("userId") @Positive Long userId, @PathVariable("payPeriodId") @Positive Long payPeriodId){
            return payPeriodService.getPayPeriodById(userId, payPeriodId);
        }

        @GetMapping("/{payPeriodId}/summary")
        public PayPeriodSummary getPayPeriodSummary(
                @PathVariable("payPeriodId") @Positive Long payPeriodId){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return payPeriodSummaryService.getPayPeriodSummary(email, payPeriodId);
        }
}
