package com.hessoune.batch.writer;

import com.hessoune.entity.Transaction;
import com.hessoune.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

// Custom ItemWriter for writing Transactions to the database
@Component
@RequiredArgsConstructor
public class TransactionWriter implements ItemWriter<Transaction> {

    // Service for handling transaction-related operations
    private final TransactionService transactionService;

    // Write method that processes a chunk of transactions
    @Override
    public void write(Chunk<? extends Transaction> transactions) throws Exception {
        // For each transaction in the chunk, invoke the createTransaction method of the service
        transactions.forEach(transactionService::createTransaction);
    }
}
