package com.hessoune.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    private Long idTransaction;
    private double montant;
    private LocalDate dateTransaction;
    private LocalDate dateDebit;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_id")
    private Compte compte;
}
