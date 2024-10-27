package org.ensa.epreuve.repository;


import org.ensa.epreuve.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
}
