package com.collins.leftover.service;

import com.collins.leftover.dto.recurringexpense.CreateRecurringExpenseRequestDto;
import com.collins.leftover.dto.recurringexpense.RecurringExpenseResponseDto;
import com.collins.leftover.model.RecurringExpense;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.RecurringExpenseRepository;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecurringExpenseService {

    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserRepository userRepository;

    public RecurringExpenseResponseDto createRecurringExpense(String email, CreateRecurringExpenseRequestDto dto) {
        User user = getUserByEmail(email);

        RecurringExpense recurringExpense = new RecurringExpense(
                user,
                dto.getName(),
                dto.getAmount(),
                dto.getRecurringType(),
                true
        );

        recurringExpenseRepository.save(recurringExpense);

        return mapToRecurringExpenseResponseDto(recurringExpense);
    }

    public List<RecurringExpenseResponseDto> getActiveRecurringExpenses(String email) {
        User user = getUserByEmail(email);

        return recurringExpenseRepository.findAllByUser_Id(user.getId())
                .stream()
                .filter(RecurringExpense::isActive)
                .map(this::mapToRecurringExpenseResponseDto)
                .toList();
    }

    public RecurringExpenseResponseDto getRecurringExpenseById(String email, Long expenseId) {
        User user = getUserByEmail(email);

        RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No recurring expense found with that id for this user"
                ));

        return mapToRecurringExpenseResponseDto(recurringExpense);
    }

    public void deactivateRecurringExpense(String email, Long expenseId) {
        User user = getUserByEmail(email);

        RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No recurring expense found with that id for this user"
                ));

        recurringExpense.setActive(false);
        recurringExpenseRepository.save(recurringExpense);
    }

    public void deleteRecurringExpense(String email, Long expenseId) {
        User user = getUserByEmail(email);

        RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No recurring expense found with that id for this user"
                ));

        recurringExpenseRepository.delete(recurringExpense);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with that email not found!"
                ));
    }

    private RecurringExpenseResponseDto mapToRecurringExpenseResponseDto(RecurringExpense recurringExpense) {
        return new RecurringExpenseResponseDto(
                recurringExpense.getId(),
                recurringExpense.getName(),
                recurringExpense.getAmount(),
                recurringExpense.getRecurringType(),
                recurringExpense.isActive(),
                recurringExpense.getCreatedAt(),
                recurringExpense.getUpdatedAt()
        );
    }
}