package org.ensa.examin.repository;

import org.ensa.examin.entities.Examin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminRepository extends JpaRepository<Examin,Long> {
}
