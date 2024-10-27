package org.ensa.conactlaboratoire.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class contactLaboratoire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Long fkIdLaboratoire;
    private Long fkIdAdresse;
    private Long numTel;
    private Long fax;
    private String email ;



}
