package org.ensa.serviceexamination.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.ensa.serviceexamination.entities.AuditLog;
import org.ensa.serviceexamination.repositories.AuditLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @AfterReturning(pointcut = "execution(* org.ensa.serviceexamination.controllers.*.create*(..))", returning = "response")
    public void logCreateExamin(JoinPoint joinPoint, ResponseEntity<?> response) {
        if (response.getStatusCode() == HttpStatus.CREATED) {
            Object createdEntity = joinPoint.getArgs()[0]; // Get the created entity from arguments
            String details = "Created entity: " + createdEntity.toString();
            logAction(joinPoint, "Création", details);
        }
    }


    @AfterReturning(pointcut = "execution(* org.ensa.serviceexamination.controllers.*.update*(..))", returning = "response")
    public void logUpdateExamin(JoinPoint joinPoint, ResponseEntity<?> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            Long id = (Long) joinPoint.getArgs()[0]; // Get ID from arguments
            Object updatedEntity = joinPoint.getArgs()[1]; // Get updated entity from arguments
            String details = "Updated entity ID: " + id + ", New values: " + updatedEntity.toString();
            logAction(joinPoint, "Mise à jour", details);
        }
    }

    @AfterReturning(pointcut = "execution(* org.ensa.serviceexamination.controllers.*.delete*(..))", returning = "response")
    public void logDeleteExamin(JoinPoint joinPoint, ResponseEntity<Boolean> response) {
        if (response.getBody() != null && response.getBody()) {
            Long id = (Long) joinPoint.getArgs()[0]; // First argument is the ID
            String details = "Deleted entity ID: " + id;
            logAction(joinPoint, "Suppression", details);
        }
    }

    private void logAction(JoinPoint joinPoint, String action, String details) {
        // Get the authenticated user's name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Utilisateur anonyme";

        // Get the name of the method being called
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String targetEntity = signature.getDeclaringTypeName() + "." + signature.getName();

        // Create a new audit log entry
        AuditLog auditLog = new AuditLog(username, action, targetEntity, details);

        // Save the audit log to the database
        auditLogRepository.save(auditLog);
    }
}
