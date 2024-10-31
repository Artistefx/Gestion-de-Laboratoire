package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.Examin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminRepository extends JpaRepository<Examin,Long> {
}
