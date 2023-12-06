package com.hessoune.batch.processor;

import com.hessoune.dto.TransactionDto;
import com.hessoune.entity.Compte;
import com.hessoune.entity.Transaction;
import com.hessoune.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TransactionProcessor implements ItemProcessor<TransactionDto, Transaction> {

    private final CompteService compteService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Process method that converts TransactionDto to Transaction
    @Override
    public Transaction process(TransactionDto item) {
        // Retrieve the Compte information based on the CompteService
        Compte compte = compteService.getCompteById(item.idCompte());

        // Parse the date from the TransactionDto and calculate the debit date (1 month later)
        LocalDate dateTransaction = LocalDate.parse(item.dateTransaction(), dateTimeFormatter);
        LocalDate dateDebit = dateTransaction.plusMonths(1).withDayOfMonth(1);

        // Create a new Transaction object using the processed information
        return new Transaction(
                item.idTransaction(),
                item.montant(),
                dateTransaction,
                dateDebit,
                compte
        );
    }
}
