package com.hessoune.service;

import com.hessoune.entity.Transaction;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long transactionId);
}
