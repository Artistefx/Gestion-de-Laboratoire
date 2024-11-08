package org.ensa.servicelaboratoire.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor

public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numVoie;
    private String nomVoie ;
    private Long codePostal;
    private String ville ;
    private String commmune ;

    @OneToOne(mappedBy = "adresse")
    private ContactLaboratoire contactLaboratoire;
}