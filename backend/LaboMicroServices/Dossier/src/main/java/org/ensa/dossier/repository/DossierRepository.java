package org.ensa.dossier.repository;

import org.ensa.dossier.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
}
