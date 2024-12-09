package org.ensa.serviceanalyses.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;
    private String targetEntity;
    private LocalDateTime timestamp;
    @Lob
    private String details;

    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    public AuditLog(String username, String action, String targetEntity, String details) {
        this.username = username;
        this.action = action;
        this.targetEntity = targetEntity;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
