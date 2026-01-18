package com.collins.leftover.repository;

import com.collins.leftover.model.PayPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PayPeriodRepository extends JpaRepository<PayPeriod, Long> {
    List<PayPeriod> findAllByUser_IdOrderByStartDateDesc(Long userId);
    Optional<PayPeriod> findByIdAndUser_Id(Long id, Long userId);
    Optional<PayPeriod> findFirstByUser_IdOrderByStartDateDesc(Long userId);
    Optional<PayPeriod> findFirstByUser_IdAndStartDateLessThanEqualOrderByStartDateDesc(Long userId, LocalDate date);
}
