package org.ensa.serviceutilisateurs.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoireDTO {
    private Long id;
    private String nom;
    private String logo; // Base64 encoded string
    private Long nrc;
    private boolean active;
    private LocalDate dateActivation;
}

