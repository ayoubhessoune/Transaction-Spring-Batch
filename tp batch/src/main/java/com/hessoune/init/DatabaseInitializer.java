package com.hessoune.init;

import com.hessoune.entity.Compte;
import com.hessoune.repository.CompteRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    // Repository for handling Compte entities in the database
    private final CompteRepository compteRepository;

    // PostConstruct method to initialize the database with Compte entities
    @PostConstruct
    @Transactional
    public void init() {
        // Create a Compte entity with an initial balance of 10000
        Compte compte = new Compte();
        compte.setSolde(10000);

        // If there are already records in the Compte repository, skip the initialization
        if (compteRepository.count() != 0)
            return;

        // Populate the Compte repository with 10 Compte entities
        for (int i = 1; i <= 10; i++) {
            compte.setIdCompte((long) i);
            compteRepository.save(compte);
        }
    }
}
