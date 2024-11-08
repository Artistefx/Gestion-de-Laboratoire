package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierRepository extends JpaRepository<Dossier,Long> {
}
