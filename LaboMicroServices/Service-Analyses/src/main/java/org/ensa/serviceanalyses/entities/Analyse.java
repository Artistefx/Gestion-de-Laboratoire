package org.ensa.serviceanalyses.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analyse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fkIdLaboratoire;
    private String nom;
    private String description;

    @OneToMany(mappedBy = "analyse")
    @JsonManagedReference
    private Set<Epreuve> epreuves;

    @OneToMany(mappedBy = "analyse")
    @JsonManagedReference
    private Set<TestAnalyse> testAnalyses;
}


