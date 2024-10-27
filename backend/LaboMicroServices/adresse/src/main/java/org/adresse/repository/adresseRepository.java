package org.adresse.repository;

import org.adresse.entities.adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface adresseRepository extends JpaRepository<adresse,Long> {
}
