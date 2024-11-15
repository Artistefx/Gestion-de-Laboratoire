package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.Examin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExaminRepository extends JpaRepository<Examin,Long> {

    List<Examin> findAllByFkIdEpreuve(Long id);
    List<Examin> findAllByDossier_NumDossier(Long id);
    List<Examin> findAllByFkIdTestAnalyse(Long id);
}

