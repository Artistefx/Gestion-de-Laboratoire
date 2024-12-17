package org.ensa.serviceexamination.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Examin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "fkNumDossier" , referencedColumnName = "numDossier")
    @JsonBackReference
    private Dossier dossier;

    private Long fkIdEpreuve;
    private Long fkIdTestAnalyse;
    private String resultat;

    @PrePersist
    public void generateUniqueId() {
        if (this.uniqueId == null) {
            this.uniqueId = UUID.randomUUID().toString();
        }
    }
}
