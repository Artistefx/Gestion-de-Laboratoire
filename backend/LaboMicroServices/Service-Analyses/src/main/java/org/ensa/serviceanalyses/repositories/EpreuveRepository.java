package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
}
