package org.ensa.servicelaboratoire.controllers;



import org.ensa.servicelaboratoire.entities.Adresse;
import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.repositories.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adresse")
@CrossOrigin(origins = "http://localhost:4200")
public class AdresseRestController {


    private final AdresseRepository adresseRepository;

    public AdresseRestController(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdresse(@RequestBody Adresse Adresse) {
        adresseRepository.save(Adresse);
        return ResponseEntity.status(HttpStatus.CREATED).body(Adresse);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Adresse> getAdresseById(@PathVariable Long id) {
        Optional<Adresse> adresse = adresseRepository.findById(id);
        return adresse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Adresse> updateAdresse(@PathVariable Long id, @RequestBody Adresse updatedAdresse) {
        return adresseRepository.findById(id).map(existingAdresse -> {
            existingAdresse.setNumVoie(updatedAdresse.getNumVoie());
            existingAdresse.setNomVoie(updatedAdresse.getNomVoie());
            existingAdresse.setCodePostal(updatedAdresse.getCodePostal());
            existingAdresse.setVille(updatedAdresse.getVille());
            existingAdresse.setCommmune(updatedAdresse.getCommmune());
            Adresse savedAdresse = adresseRepository.save(existingAdresse);
            return ResponseEntity.ok(savedAdresse);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> deleteAdresse(@PathVariable Long id) {
        if (adresseRepository.existsById(id)) {
            adresseRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
