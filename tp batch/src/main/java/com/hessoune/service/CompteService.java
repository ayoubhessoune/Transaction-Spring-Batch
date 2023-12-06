package com.hessoune.service;

import com.hessoune.entity.Compte;

public interface CompteService {
    Compte createCompte(Compte compte);
    Compte getCompteById(Long compteId);
    Compte debiterCompte(Long compteId, double amount);
    Compte updateCompte(Compte compte);
}
