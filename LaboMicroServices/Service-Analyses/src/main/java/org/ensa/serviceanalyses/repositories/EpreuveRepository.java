package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {

    List<Epreuve> findAllByAnalyse_Id(Long id);
}
