package org.ensa.servicelaboratoire.entities;

import jakarta.persistence.*;

import lombok.*;


@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class ContactLaboratoire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "fkIdLaboratoire", referencedColumnName = "id")
    private Laboratoire laboratoire;

    @OneToOne
    @JoinColumn(name = "fkIdAdresse", referencedColumnName = "id")
    private Adresse adresse;
    private Long numTel;
    private Long fax;
    private String email ;
}
