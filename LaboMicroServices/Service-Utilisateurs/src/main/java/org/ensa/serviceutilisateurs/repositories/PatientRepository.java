package org.ensa.serviceutilisateurs.repositories;

import java.util.Optional;
import org.ensa.serviceutilisateurs.entities.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patients,Long> {

    boolean existsById(Long id);

    Optional<Patients> findByEmail(String email);
}
