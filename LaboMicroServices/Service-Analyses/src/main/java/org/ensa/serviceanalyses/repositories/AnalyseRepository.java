package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyseRepository extends JpaRepository<Analyse,Long> {
}
