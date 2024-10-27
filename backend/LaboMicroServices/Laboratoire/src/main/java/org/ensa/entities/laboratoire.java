package org.ensa.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "laboratoires")
public class laboratoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlaboratoire;
    private String nom;

    @Lob
    private byte[] logo;
    private Long nrc;
    private boolean active;




}

