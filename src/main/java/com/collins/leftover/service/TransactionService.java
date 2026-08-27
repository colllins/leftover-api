package com.collins.leftover.service;

import com.collins.leftover.dto.transaction.CreateTransactionRequestDto;
import com.collins.leftover.dto.transaction.TransactionResponseDto;
import com.collins.leftover.dto.transaction.UpdateTransactionDto;
import com.collins.leftover.model.PayPeriod;
import com.collins.leftover.model.Transaction;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.PayPeriodRepository;
import com.collins.leftover.repository.TransactionRepository;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PayPeriodRepository payPeriodRepository;

    public TransactionResponseDto createTransaction(String email, CreateTransactionRequestDto dto) {
        User user = getUserByEmail(email);

        List<PayPeriod> payPeriods = payPeriodRepository.findAllByUser_Id(user.getId())
                .stream()
                .filter(PayPeriod::isActive)
                .toList();

        if (payPeriods.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User has no active pay period. Create one first."
            );
        }

        PayPeriod activePayPeriod = payPeriods.get(0);

        Transaction transaction = new Transaction(
                user,
                activePayPeriod,
                dto.getType(),
                dto.getCategory(),
                dto.getAmount(),
                dto.getDate(),
                dto.getDescription()
        );

        transactionRepository.save(transaction);

        return mapToTransactionResponseDto(transaction);
    }

    public List<TransactionResponseDto> getTransactionsForPayPeriod(String email, Long payPeriodId) {
        User user = getUserByEmail(email);

        PayPeriod payPeriod = payPeriodRepository.findByIdAndUser_Id(payPeriodId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pay period not found for this user"
                ));

        List<TransactionResponseDto> transactions = new ArrayList<>();

        transactionRepository.findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(user.getId(), payPeriod.getId())
                .forEach(transaction -> transactions.add(mapToTransactionResponseDto(transaction)));

        return transactions;
    }

    public TransactionResponseDto getTransactionById(String email, Long transactionId) {
        User user = getUserByEmail(email);

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No transaction found with that id for this user"
                ));

        return mapToTransactionResponseDto(transaction);
    }

    public TransactionResponseDto updateTransaction(String email, Long transactionId, UpdateTransactionDto dto) {
        User user = getUserByEmail(email);

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No transaction found with that id for this user"
                ));

        if (dto.getType() != null) transaction.setType(dto.getType());
        if (dto.getAmount() != null) transaction.setAmount(dto.getAmount());
        if (dto.getCategory() != null) transaction.setCategory(dto.getCategory());
        if (dto.getDate() != null) transaction.setDate(dto.getDate());
        if (dto.getDescription() != null) transaction.setDescription(dto.getDescription());

        return mapToTransactionResponseDto(transaction);
    }

    public void deleteTransaction(String email, Long transactionId) {
        User user = getUserByEmail(email);

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No transaction found with that id for this user"
                ));

        transactionRepository.delete(transaction);
    }

    public List<TransactionResponseDto> getRecentTransactions(Long userId, int limit) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id not found!");
        }

        List<TransactionResponseDto> transactions = new ArrayList<>();

        transactionRepository.findAllByUser_IdOrderByDateDesc(userId)
                .stream()
                .limit(limit)
                .forEach(transaction -> transactions.add(mapToTransactionResponseDto(transaction)));

        return transactions;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with that email not found!"
                ));
    }

    private TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getDescription()
        );
    }
}