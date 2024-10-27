package org.ensa.analyse.repository;

import org.ensa.analyse.entities.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyseRepository extends JpaRepository<Analyse,Long> {
}
