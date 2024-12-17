package org.ensa.serviceexamination.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numDossier;

    @Column(unique = true, nullable = false, updatable = false)
    private String uniqueId;

    private String fkEmailUtilisateur;
    private Long fkIdPatient;
    private LocalDate date;

    @OneToMany(mappedBy = "dossier" , fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Examin>  examins;

    @PrePersist
    public void generateUniqueId() {
        if (this.uniqueId == null) {
            this.uniqueId = UUID.randomUUID().toString();
        }
    }
}
