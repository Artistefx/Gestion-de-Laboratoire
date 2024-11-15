package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyseRepository extends JpaRepository<Analyse,Long> {

    List<Analyse> findAllByFkIdLaboratoire(Long id);
}
