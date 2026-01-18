package com.collins.leftover.repository;

import com.collins.leftover.model.RecurringExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {
    List<RecurringExpense> findAllByUser_Id(Long userId);
    List<RecurringExpense> findAllByUser_IdAndActiveTrue(Long userId);
    //List<RecurringExpense> findAllByUser_IdAndNextDueDateLessThanEqual(Long userId, LocalDate date);
    Optional<RecurringExpense> findByIdAndUser_Id(Long id, Long userId);
}
