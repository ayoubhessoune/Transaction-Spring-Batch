package com.hessoune.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Compte {
    @Id
    private Long idCompte;
    private double solde;
    @OneToMany(mappedBy = "compte")
    private Set<Transaction> transactions;
}
