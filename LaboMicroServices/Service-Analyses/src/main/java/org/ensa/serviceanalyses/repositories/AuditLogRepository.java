package org.ensa.serviceanalyses.repositories;

import org.ensa.serviceanalyses.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
