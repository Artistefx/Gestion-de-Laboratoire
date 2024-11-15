package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAnalyseRepository extends JpaRepository<TestAnalyse,Long> {

    List<TestAnalyse> findAllByAnalyse_Id(Long id);
}
