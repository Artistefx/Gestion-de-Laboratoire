package org.ensa.serviceutilisateurs.controllers;


import org.ensa.serviceutilisateurs.entities.utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateursRestController {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    public UtilisateursRestController(UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<utilisateurs> utilisateursList() {
        return utilisateursRepository.findAll();
    }

    // Récupérer un utilisateur par son email
    @GetMapping("/{email}")
    public ResponseEntity<utilisateurs> getUtilisateurByEmail(@PathVariable String email) {
        Optional<utilisateurs> utilisateur = utilisateursRepository.findById(email);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public utilisateurs addUtilisateur(@RequestBody utilisateurs newUtilisateur) {
        return utilisateursRepository.save(newUtilisateur);
    }


    @PutMapping("/{email}")
    public ResponseEntity<utilisateurs> updateUtilisateur(@PathVariable String email, @RequestBody utilisateurs updatedUtilisateur) {
        return utilisateursRepository.findById(email).map(existingUtilisateur -> {
            existingUtilisateur.setFkIdLaboratoire(updatedUtilisateur.getFkIdLaboratoire());
            existingUtilisateur.setNomComplet(updatedUtilisateur.getNomComplet());
            existingUtilisateur.setProfession(updatedUtilisateur.getProfession());
            existingUtilisateur.setNumTel(updatedUtilisateur.getNumTel());
            existingUtilisateur.setSignature(updatedUtilisateur.getSignature());
            existingUtilisateur.setRole(updatedUtilisateur.getRole());
            utilisateursRepository.save(existingUtilisateur);
            return ResponseEntity.ok(existingUtilisateur);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String email) {
        if (utilisateursRepository.existsById(email)) {
            utilisateursRepository.deleteById(email);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
