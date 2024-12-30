package org.ensa.servicelaboratoire.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Laboratoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;


    private String logo;
    private Long nrc;
    private boolean active;
    private LocalDate dateActivation;

    @OneToMany(mappedBy = "laboratoire" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ContactLaboratoire> contacts;
}

