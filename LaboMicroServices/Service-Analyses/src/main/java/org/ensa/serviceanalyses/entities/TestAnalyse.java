package org.ensa.serviceanalyses.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestAnalyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nomTest;

    private String sousEpreuve;

    private Double intervalMinDeReference;
    private Double intervalMaxDeReference;
    private String uniteDeReference;
    private String details;

    @ManyToOne
    @JoinColumn(name = "fkIdAnalyse", referencedColumnName = "id")
    private Analyse analyse;
}
