package org.ensa.servicelaboratoire.repositories;

import org.ensa.servicelaboratoire.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
