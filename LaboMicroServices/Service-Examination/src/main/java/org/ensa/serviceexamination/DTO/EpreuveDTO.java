package org.ensa.serviceexamination.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpreuveDTO {

    private Long id;
    private AnalyseDTO analyse;
    private String nom;
}
