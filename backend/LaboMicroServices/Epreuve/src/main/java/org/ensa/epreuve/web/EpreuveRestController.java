package org.ensa.epreuve.web;

import org.ensa.epreuve.entities.Epreuve;
import org.ensa.epreuve.repository.EpreuveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Epreuve")
public class EpreuveRestController {

    @Autowired
    private EpreuveRepository epreuveRepository;

    public EpreuveRestController(EpreuveRepository epreuveRepository) {
        this.epreuveRepository = epreuveRepository;
    }


    @GetMapping
    public List<Epreuve> getAllEpreuves() {
        return epreuveRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Epreuve> getEpreuveById(@PathVariable Long id) {
        Optional<Epreuve> epreuve = epreuveRepository.findById(id);
        return epreuve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public Epreuve createEpreuve(@RequestBody Epreuve newEpreuve) {
        return epreuveRepository.save(newEpreuve);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Epreuve> updateEpreuve(@PathVariable Long id, @RequestBody Epreuve updatedEpreuve) {
        return epreuveRepository.findById(id).map(existingEpreuve -> {
            existingEpreuve.setFkIdAnalyse(updatedEpreuve.getFkIdAnalyse());
            existingEpreuve.setNom(updatedEpreuve.getNom());
            epreuveRepository.save(existingEpreuve);
            return ResponseEntity.ok(existingEpreuve);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpreuve(@PathVariable Long id) {
        if (epreuveRepository.existsById(id)) {
            epreuveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
