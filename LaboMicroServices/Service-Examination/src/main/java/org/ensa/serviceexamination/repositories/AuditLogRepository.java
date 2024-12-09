package org.ensa.serviceexamination.repositories;

import org.ensa.serviceexamination.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
