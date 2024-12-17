package org.ensa.serviceanalyses.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Epreuve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fkIdAnalyse", referencedColumnName = "id")
    @JsonBackReference
    private Analyse analyse;
    private String nom;

    @OneToOne
    private TestAnalyse testAnalyse;
}