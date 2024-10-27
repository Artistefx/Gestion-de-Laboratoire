package org.adresse.web;

import org.adresse.entities.adresse;
import org.adresse.repository.adresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adresse")
public class adresseRestController {

    @Autowired
    private adresseRepository adresseRepository;


    public adresseRestController(adresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }


    @PostMapping
    public adresse createAdresse(@RequestBody adresse adresse) {
        return adresseRepository.save(adresse);
    }


    @GetMapping
    public List<adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }


    @GetMapping("/{idAdresse}")
    public ResponseEntity<adresse> getAdresseById(@PathVariable Long idAdresse) {
        Optional<adresse> adresse = adresseRepository.findById(idAdresse);
        return adresse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{idAdresse}")
    public ResponseEntity<adresse> updateAdresse(@PathVariable Long idAdresse, @RequestBody adresse updatedAdresse) {
        return adresseRepository.findById(idAdresse).map(existingAdresse -> {
            existingAdresse.setNumVoie(updatedAdresse.getNumVoie());
            existingAdresse.setNomVoie(updatedAdresse.getNomVoie());
            existingAdresse.setCodePostal(updatedAdresse.getCodePostal());
            existingAdresse.setVille(updatedAdresse.getVille());
            existingAdresse.setCommmune(updatedAdresse.getCommmune());
            adresse savedAdresse = adresseRepository.save(existingAdresse);
            return ResponseEntity.ok(savedAdresse);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{idAdresse}")
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long idAdresse) {
        if (adresseRepository.existsById(idAdresse)) {
            adresseRepository.deleteById(idAdresse);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
