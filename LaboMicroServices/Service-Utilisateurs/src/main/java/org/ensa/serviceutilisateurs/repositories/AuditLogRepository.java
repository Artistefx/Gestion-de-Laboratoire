package org.ensa.serviceutilisateurs.repositories;

import org.ensa.serviceutilisateurs.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
