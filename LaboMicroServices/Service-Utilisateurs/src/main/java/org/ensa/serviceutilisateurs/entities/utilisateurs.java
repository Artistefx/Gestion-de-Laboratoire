package org.ensa.serviceutilisateurs.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @AllArgsConstructor @NoArgsConstructor
@Setter
public class utilisateurs {
    @Id
    private String email;
    private Long fkIdLaboratoire;
    private String nomComplet;
    private String profession ;
    private Long numTel;
    private String signature;
    private String role;


}