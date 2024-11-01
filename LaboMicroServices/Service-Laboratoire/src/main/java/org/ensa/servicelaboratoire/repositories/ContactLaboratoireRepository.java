package org.ensa.servicelaboratoire.repositories;

import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactLaboratoireRepository extends JpaRepository<ContactLaboratoire,Long> {
}
