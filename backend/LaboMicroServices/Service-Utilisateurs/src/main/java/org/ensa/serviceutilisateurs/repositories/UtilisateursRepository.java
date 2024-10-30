package org.ensa.serviceutilisateurs.repositories;

import org.ensa.serviceutilisateurs.entities.utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UtilisateursRepository extends JpaRepository<utilisateurs, String> {

}

