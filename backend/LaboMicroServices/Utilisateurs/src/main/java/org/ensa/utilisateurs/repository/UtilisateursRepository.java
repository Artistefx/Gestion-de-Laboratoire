package org.ensa.utilisateurs.repository;
import org.ensa.utilisateurs.entities.utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UtilisateursRepository extends JpaRepository<utilisateurs, String> {

}
