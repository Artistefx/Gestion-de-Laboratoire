package org.ensa.serviceexamination.DTO;

import lombok.Data;

@Data
public class PatientDTO {
        private Long idPatient;
        private String nomComplet;
        private String dateNaissance;
        private String lieuDeNaissance;
        private String sexe;
        private String numPieceIdentite;
        private String adresse;
        private String numTel;
        private String email;
        private String visible_pour;
        private Long fkIdLaboratoire;
}
