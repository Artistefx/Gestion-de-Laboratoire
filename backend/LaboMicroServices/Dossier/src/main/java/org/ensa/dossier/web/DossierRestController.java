package org.ensa.dossier.web;

import org.ensa.dossier.entities.Dossier;
import org.ensa.dossier.repository.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Dossier")
public class DossierRestController {

    @Autowired
    private DossierRepository dossierRepository;


    public DossierRestController(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }


    @PostMapping
    public Dossier createDossier(@RequestBody Dossier dossier) {
        return dossierRepository.save(dossier);
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


    @PutMapping("/{numDossier}")
    public ResponseEntity<Dossier> updateDossier(@PathVariable Long numDossier, @RequestBody Dossier updatedDossier) {
        return dossierRepository.findById(numDossier).map(existingDossier -> {
            existingDossier.setFkIdUtilisateur(updatedDossier.getFkIdUtilisateur());
            existingDossier.setFkIdPatient(updatedDossier.getFkIdPatient());
            existingDossier.setDate(updatedDossier.getDate());
            Dossier savedDossier = dossierRepository.save(existingDossier);
            return ResponseEntity.ok(savedDossier);
        }).orElseGet(() -> ResponseEntity.notFound().build());
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
