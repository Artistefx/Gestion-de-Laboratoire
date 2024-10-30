package org.ensa.serviceutilisateurs.repositories;

import org.ensa.serviceutilisateurs.entities.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patients,Long> {
}
