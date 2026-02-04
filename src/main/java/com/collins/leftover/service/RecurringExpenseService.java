package com.collins.leftover.service;

import com.collins.leftover.dto.recurringexpense.CreateRecurringExpenseRequestDto;
import com.collins.leftover.dto.recurringexpense.RecurringExpenseResponseDto;
import com.collins.leftover.model.RecurringExpense;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.RecurringExpenseRepository;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecurringExpenseService {

    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserRepository userRepository;

    public RecurringExpenseService(RecurringExpenseRepository recurringExpenseRepository, UserRepository userRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.userRepository = userRepository;
    }

    public RecurringExpenseResponseDto createRecurringExpense(Long userId, CreateRecurringExpenseRequestDto dto){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id not found!"));

        RecurringExpense recurringExpense = new RecurringExpense(user, dto.getName(), dto.getAmount(), dto.getRecurringType(), true);
        recurringExpenseRepository.save(recurringExpense);

        return new RecurringExpenseResponseDto(recurringExpense.getId(),
                recurringExpense.getName(),
                recurringExpense.getAmount(),
                recurringExpense.getRecurringType(),
                recurringExpense.isActive(),
                recurringExpense.getCreatedAt(),
                recurringExpense.getUpdatedAt()
        );
    }

    public List<RecurringExpenseResponseDto> getActiveRecurringExpenses(Long userId){

        List<RecurringExpenseResponseDto> recurringExpenses = new ArrayList<>();

        if(!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!");

        return recurringExpenseRepository.findAllByUser_Id(userId).stream()
                .filter(RecurringExpense::isActive)
                .map(r -> new RecurringExpenseResponseDto(
                                r.getId(),
                                r.getName(),
                                r.getAmount(),
                                r.getRecurringType(),
                                r.isActive(),
                                r.getCreatedAt(),
                                r.getUpdatedAt()
                        )).toList();


    }

    public RecurringExpenseResponseDto getRecurringExpenseById(Long userId, Long expenseId){
        RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Recurring Expense found with that id"));

        return new RecurringExpenseResponseDto(recurringExpense.getId(),
                recurringExpense.getName(),
                recurringExpense.getAmount(),
                recurringExpense.getRecurringType(),
                recurringExpense.isActive(),
                recurringExpense.getCreatedAt(),
                recurringExpense.getUpdatedAt()
                );
    }

   public void deactivateRecurringExpense(Long userId, Long expenseId){
       RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, userId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Recurring Expense found with that id"));
       recurringExpense.setActive(false);
       recurringExpenseRepository.save(recurringExpense);
   }

   public void deleteRecurringExpense(Long userId, Long expenseId){
       RecurringExpense recurringExpense = recurringExpenseRepository.findByIdAndUser_Id(expenseId, userId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Recurring Expense found with that id"));

       recurringExpenseRepository.delete(recurringExpense);
   }
}
