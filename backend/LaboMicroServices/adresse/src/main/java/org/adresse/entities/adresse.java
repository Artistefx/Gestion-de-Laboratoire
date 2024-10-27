package org.adresse.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name ="Adresse")
public class adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdresse;
    private Long numVoie;
    private String NomVoie ;
    private Long codePostal;
    private String ville ;
    private String commmune ;


}
