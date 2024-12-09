package org.ensa.serviceanalyses.services;

import org.ensa.serviceanalyses.entities.AuditLog;
import org.ensa.serviceanalyses.repositories.AuditLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public String getCurrentUsername() {
        System.out.println("triggered");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void AddAuditLog(String action, String targetEntity, String details){
        System.out.println("triggered1");
        String username = getCurrentUsername();
        AuditLog log = new AuditLog(username, action, targetEntity, details);
        auditLogRepository.save(log);
    }
}
