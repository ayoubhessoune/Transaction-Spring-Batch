package com.hessoune.service.impl;

import com.hessoune.entity.Transaction;
import com.hessoune.repository.TransactionRepository;
import com.hessoune.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    // Repository for handling Transaction entities in the database
    private final TransactionRepository transactionRepository;

    // Create a new Transaction in the database
    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Check if a Transaction with the given ID already exists
        if (transaction.getIdTransaction() != null && transactionRepository.existsById(transaction.getIdTransaction())) {
            throw new EntityNotFoundException("Transaction already exists with id " + transaction.getIdTransaction());
        }
        // Save the new Transaction to the database
        return transactionRepository.save(transaction);
    }

    // Retrieve a Transaction from the database by its ID
    @Override
    public Transaction getTransactionById(Long transactionId) {
        // Find and return the Transaction with the given ID or throw an exception if not found
        return transactionRepository.findById(transactionId).orElseThrow(
                () -> new EntityNotFoundException("Transaction not found with id " + transactionId)
        );
    }
}
