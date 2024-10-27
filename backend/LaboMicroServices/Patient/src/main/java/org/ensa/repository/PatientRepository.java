package org.ensa.repository;

import org.ensa.entities.patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<patient,Long> {
}
