package com.hessoune.batch.processor;

import com.hessoune.entity.Transaction;
import com.hessoune.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompteProcessor implements ItemProcessor<Transaction, Transaction> {

    private final CompteService compteService;

    // Process method that applies business logic to each item (Transaction)
    @Override
    public Transaction process(Transaction transaction) throws Exception {
        // Call the CompteService to debit the account based on the transaction details
        compteService.debiterCompte(transaction.getCompte().getIdCompte(), transaction.getMontant());

        // The processed transaction is returned (unchanged in this case)
        return transaction;
    }
}
