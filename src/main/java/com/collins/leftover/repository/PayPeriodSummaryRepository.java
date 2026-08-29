package com.collins.leftover.repository;

import com.collins.leftover.model.PayPeriodSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayPeriodSummaryRepository extends JpaRepository<PayPeriodSummary, Long> {
    Optional<PayPeriodSummary> findAllByPayPeriod_Id(Long payPeriodId);

    boolean existsByPayPeriod_Id(Long payPeriodId);

    Optional<PayPeriodSummary> findByPayPeriod_IdAndUser_Email(Long payPeriodId, String email);
    Optional<PayPeriodSummary> findByPayPeriod_IdAndUser_Id(Long payPeriodId, Long userId);
}