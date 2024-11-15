package org.ensa.serviceexamination.controllers;

import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.repositories.DossierRepository;
import org.ensa.serviceexamination.services.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dossiers")
public class DossierRestController {

    private final DossierRepository dossierRepository;
    private final DossierService dossierService;


    public DossierRestController(DossierRepository dossierRepository, DossierService dossierService) {
        this.dossierRepository = dossierRepository;
        this.dossierService = dossierService;
    }

    @PostMapping
    public ResponseEntity<?> createDossier(@RequestBody Dossier dossier) {
        try{
            dossierService.CreateOrUpdateDossier(dossier);
            return new ResponseEntity<>(dossier, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<Dossier> getAllDossiers() {
        return dossierRepository.findAll();
    }

    @GetMapping("/{numDossier}")
    public ResponseEntity<Dossier> getDossierById(@PathVariable Long numDossier) {
        Optional<Dossier> dossier = dossierRepository.findById(numDossier);
        return dossier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byUtilisateur/{email}")
    public List<Dossier> getDossierByIdUtilistateur(@PathVariable String email) {
        return dossierRepository.findAllByFkEmailUtilisateur(email);
    }

    @GetMapping("/byPassion/{id}")
    public List<Dossier> getDossierByIdPassion(@PathVariable Long id) {
        return dossierRepository.findAllByFkIdPatient(id);
    }

    @PutMapping("/{numDossier}")
    public ResponseEntity<?> updateDossier(@PathVariable Long numDossier, @RequestBody Dossier updatedDossier) {
        try {
            return dossierRepository.findById(numDossier).map(existingDossier -> {
                existingDossier.setFkEmailUtilisateur(updatedDossier.getFkEmailUtilisateur());
                existingDossier.setFkIdPatient(updatedDossier.getFkIdPatient());
                existingDossier.setDate(updatedDossier.getDate());
                existingDossier.setExamins(updatedDossier.getExamins());
                dossierService.CreateOrUpdateDossier(existingDossier);
                return new ResponseEntity<>(existingDossier, HttpStatus.OK);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{numDossier}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long numDossier) {
        if (dossierRepository.existsById(numDossier)) {
            dossierRepository.deleteById(numDossier);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
