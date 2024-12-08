package org.ensa.serviceexamination.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numDossier;
    private String fkEmailUtilisateur;
    private Long fkIdPatient;
    private LocalDate date;

    @OneToMany(mappedBy = "dossier" , fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Examin>  examins;
}
