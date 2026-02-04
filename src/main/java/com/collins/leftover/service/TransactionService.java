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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PayPeriodRepository payPeriodRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, PayPeriodRepository payPeriodRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.payPeriodRepository = payPeriodRepository;
    }

    public TransactionResponseDto createTransaction(Long userId, CreateTransactionRequestDto dto){
        //check if user exists
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!"));


        List<PayPeriod> payPeriods = payPeriodRepository.findAllByUser_Id(userId).stream().filter(PayPeriod::isActive).toList();

        if (payPeriods.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User has no active pay period. Create one first."
            );
        }

        PayPeriod activePayPeriod = payPeriods.get(0);

        Transaction transaction = new Transaction(user, activePayPeriod, dto.getType(),dto.getCategory(), dto.getAmount(), dto.getDate(), dto.getDescription());
        transactionRepository.save(transaction);

        return new TransactionResponseDto(transaction.getId(), transaction.getType(), transaction.getAmount(), transaction.getCategory(), transaction.getDate(), transaction.getDescription());
    }

    public List<TransactionResponseDto> getTransactionsForPayPeriod(Long userId, Long payPeriodId){

        List<TransactionResponseDto> transactions = new ArrayList<>();

        //check if user exists
        if(!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!");

        //check if the pay period is tied to the user
        PayPeriod payPeriod = payPeriodRepository.findById(payPeriodId)
                        .filter(p->p.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pay period not found for this user"));

        //get all transactions for this user in that pay period then create a transaction response for each and return the list
      transactionRepository.findAllByUser_IdAndPayPeriod_IdOrderByDateDesc(userId, payPeriodId).forEach(transaction ->
//          TransactionResponseDto dto = new TransactionResponseDto(transaction.getId(), transaction.getType(), transaction.getAmount(), transaction.getCategory(), transaction.getDate(), transaction.getDescription());

          transactions.add(
                  new TransactionResponseDto(
                          transaction.getId(),
                          transaction.getType(),
                          transaction.getAmount(),
                          transaction.getCategory(),
                          transaction.getDate(),
                          transaction.getDescription())
                  )
      );

      return transactions;
    }

    public TransactionResponseDto getTransactionById(Long userId, Long transactionId){

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Transaction found with that id"));

        return new TransactionResponseDto(transaction.getId(), transaction.getType(), transaction.getAmount(), transaction.getCategory(), transaction.getDate(), transaction.getDescription());
    }

    public TransactionResponseDto updateTransaction(Long userId, Long transactionId, UpdateTransactionDto dto){

        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!"));

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Transaction found with that id"));


        if(dto.getType()!=null) transaction.setType(dto.getType());

        if(dto.getAmount()!=null) transaction.setAmount(dto.getAmount());

        if(dto.getCategory()!=null) transaction.setCategory(dto.getCategory());

        if(dto.getDate()!=null) transaction.setDate(dto.getDate());

        if(dto.getDescription()!=null) transaction.setDescription(dto.getDescription());


        return new TransactionResponseDto(transaction.getId(), transaction.getType(), transaction.getAmount(), transaction.getCategory(), transaction.getDate(), transaction.getDescription());
    }

    public void deleteTransaction(Long userId, Long transactionId){

        Transaction transaction = transactionRepository.findByIdAndUser_Id(transactionId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Transaction found with that id"));

        transactionRepository.delete(transaction);
    }

    public List<TransactionResponseDto> getRecentTransactions(Long userId, int limit){

        List<TransactionResponseDto> transactions = new ArrayList<>();

        //check if user exists
        if(!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!");

         transactionRepository.findAllByUser_IdOrderByDateDesc(userId).stream().limit(limit).forEach(transaction ->
             transactions.add(
                     new TransactionResponseDto(
                             transaction.getId(),
                             transaction.getType(),
                             transaction.getAmount(),
                             transaction.getCategory(),
                             transaction.getDate(),
                             transaction.getDescription()
                     )
             )
         );

         return transactions;
    }
}
