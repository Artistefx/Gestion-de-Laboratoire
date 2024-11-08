package org.ensa.servicelaboratoire.repositories;

import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactLaboratoireRepository extends JpaRepository<ContactLaboratoire,Long> {

    Optional<ContactLaboratoire> findByAdresseId(Long id);

}
