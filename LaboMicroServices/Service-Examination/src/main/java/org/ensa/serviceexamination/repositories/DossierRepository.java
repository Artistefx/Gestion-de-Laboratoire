package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier,Long> {
}
