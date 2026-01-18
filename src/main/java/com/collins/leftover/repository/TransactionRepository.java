package com.collins.leftover.repository;

import com.collins.leftover.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser_IdOrderByDateDesc(Long userId);
    List<Transaction> findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(Long userId, Long payPeriodId);
    List<Transaction> findAllByUser_IdAndDateBetweenOrderByDateAsc(Long userId, LocalDate from, LocalDate to);
    List<Transaction> findTop10ByUser_IdOrderByDateDesc(Long userId);
    Optional<Transaction> findByIdAndUser_Id(Long id, Long userId);
}
