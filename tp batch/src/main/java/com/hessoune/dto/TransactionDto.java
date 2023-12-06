package com.hessoune.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("transaction")
public final class TransactionDto {
    @XStreamAsAttribute
    private final Long idTransaction;
    private final Long idCompte;
    private final Double montant;
    private final String dateTransaction;

    public TransactionDto(Long idTransaction, Long idCompte, Double montant, String dateTransaction) {
        this.idTransaction = idTransaction;
        this.idCompte = idCompte;
        this.montant = montant;
        this.dateTransaction = dateTransaction;
    }

    public Long idTransaction() {
        return idTransaction;
    }

    public Long idCompte() {
        return idCompte;
    }

    public Double montant() {
        return montant;
    }

    public String dateTransaction() {
        return dateTransaction;
    }
}
