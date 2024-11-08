package org.ensa.serviceutilisateurs.repositories;

import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs, String> {

    boolean existsUtilisateurByEmail(String email);
}

