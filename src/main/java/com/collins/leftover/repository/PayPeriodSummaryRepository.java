package com.collins.leftover.repository;

import com.collins.leftover.model.PayPeriodSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayPeriodSummaryRepository extends JpaRepository<PayPeriodSummary, Long> {
    Optional<PayPeriodSummary> findByPayPeriodId(Long payPeriodId);

    boolean existsByPayPeriodId(Long payPeriodId);

    Optional<PayPeriodSummary> findByPayPeriodIdAndUserEmail(Long payPeriodId, String email);
}
