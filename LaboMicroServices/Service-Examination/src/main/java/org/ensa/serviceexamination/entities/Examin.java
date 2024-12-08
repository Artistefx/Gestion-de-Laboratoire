package org.ensa.serviceexamination.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Examin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fkNumDossier" , referencedColumnName = "numDossier")
    @JsonBackReference
    private Dossier dossier;

    private Long fkIdEpreuve;
    private Long fkIdTestAnalyse;
    private String resultat;

}
