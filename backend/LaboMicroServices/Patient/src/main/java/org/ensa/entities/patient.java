package org.ensa.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatient;

    private String nomComplet;
    private String dateNaissance;
    private String lieuDeNaissance;
    private String sexe;

    private String numPieceIdentite ;

    private String adresse;

    private String numTel;
    private String email;
    private String visible_pour;



}



