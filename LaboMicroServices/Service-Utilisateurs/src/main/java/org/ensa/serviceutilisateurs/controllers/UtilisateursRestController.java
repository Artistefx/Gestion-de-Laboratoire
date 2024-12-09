package org.ensa.serviceutilisateurs.controllers;


import feign.FeignException;
import org.ensa.serviceutilisateurs.DTOs.LaboratoireDTO;
import org.ensa.serviceutilisateurs.clients.LaboratoireClient;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.ensa.serviceutilisateurs.services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateursRestController {

    private final UtilisateursRepository utilisateursRepository;
    private final UtilisateursService utilisateursService;

    public UtilisateursRestController(UtilisateursRepository utilisateursRepository ,
                                      UtilisateursService utilisateursService
                                      ) {
        this.utilisateursRepository = utilisateursRepository;
        this.utilisateursService = utilisateursService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Utilisateurs> utilisateursList() {
        return utilisateursRepository.findAll();
    }


    @GetMapping("/{email}")
    public ResponseEntity<Utilisateurs> getUtilisateurByEmail(@PathVariable String email) {
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(email);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byLabo/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Utilisateurs>> getAllByIdLabo(@PathVariable("id") Long id){
        return ResponseEntity.ok(utilisateursRepository.findAllByFkIdLaboratoire(id));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> utilisateurExists(@PathVariable String email) {
        if (utilisateursRepository.existsById(email)){
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUtilisateur(@RequestBody Utilisateurs newUtilisateur) {
            try{
                Utilisateurs savedUser = utilisateursService.createUtilisateur(newUtilisateur);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            } catch (FeignException.NotFound e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inconnue");
            }
    }


    @PutMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUtilisateur(@PathVariable String email, @RequestBody Utilisateurs updatedUtilisateur) {
        try{
            Utilisateurs savedUser =  utilisateursService.updateContactLaboratoire(email , updatedUtilisateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur: Une erreur est survenue lors de la mise a jour de l'utilisateur.");
        }
    }


    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> deleteUtilisateur(@PathVariable String email) {
        if (utilisateursRepository.existsById(email)) {
            utilisateursRepository.deleteById(email);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
