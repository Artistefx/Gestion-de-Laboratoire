package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier,Long> {

    List<Dossier> findAllByFkEmailUtilisateur(String email);
    List<Dossier> findAllByFkIdPatient(Long id);
}
