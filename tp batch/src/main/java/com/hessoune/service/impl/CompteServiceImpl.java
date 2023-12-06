package com.hessoune.service.impl;

import com.hessoune.entity.Compte;
import com.hessoune.repository.CompteRepository;
import com.hessoune.service.CompteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService {

    // Repository for handling Compte entities in the database
    private final CompteRepository compteRepository;

    // Create a new Compte in the database
    @Override
    public Compte createCompte(Compte compte) {
        // Check if a Compte with the given ID already exists
        if (compte.getIdCompte() != null && compteRepository.existsById(compte.getIdCompte())) {
            throw new EntityNotFoundException("Compte already exists with id " + compte.getIdCompte());
        }
        // Save the new Compte to the database
        return compteRepository.save(compte);
    }

    // Retrieve a Compte from the database by its ID
    @Override
    public Compte getCompteById(Long compteId) {
        // Find and return the Compte with the given ID or throw an exception if not found
        return compteRepository.findById(compteId).orElseThrow(
                () -> new EntityNotFoundException("Compte not found with id " + compteId)
        );
    }

    // Debit the specified Compte by a given amount
    @Override
    public Compte debiterCompte(Long compteId, double amount) {
        // Find the Compte with the given ID or throw an exception if not found
        Compte compte = compteRepository.findById(compteId).orElseThrow(
                () -> new EntityNotFoundException("Compte not found with id " + compteId)
        );

        // Validate the transaction amount
        if (amount <= 0 || compte.getSolde() < amount) {
            throw new RuntimeException("Invalid transaction");
        }

        // Update the Compte's balance after debiting
        double newSolde = compte.getSolde() - amount;
        compte.setSolde(newSolde);

        // Return the updated Compte
        return compte;
    }

    // Update an existing Compte in the database
    @Override
    public Compte updateCompte(Compte compte) {
        // Save the updated Compte to the database
        return compteRepository.save(compte);
    }
}
