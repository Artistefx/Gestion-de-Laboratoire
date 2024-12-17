package org.ensa.serviceexamination.DTO;

import lombok.Data;

@Data
public class AnalyseDTO {

    private Long id;
    private Long fkIdLaboratoire;
    private String nom;
    private String description;
}
