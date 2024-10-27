package org.ensa.examin.web;

import org.ensa.examin.entities.Examin;
import org.ensa.examin.repository.ExaminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Examin")
public class ExaminRestController {

    @Autowired
    private ExaminRepository examinRepository;

    // Constructor
    public ExaminRestController(ExaminRepository examinRepository) {
        this.examinRepository = examinRepository;
    }


    @PostMapping
    public Examin createExamin(@RequestBody Examin examin) {
        return examinRepository.save(examin);
    }


    @GetMapping
    public List<Examin> getAllExamin() {
        return examinRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Examin> getExaminById(@PathVariable Long id) {
        Optional<Examin> examin = examinRepository.findById(id);
        return examin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Examin> updateExamin(@PathVariable Long id, @RequestBody Examin updatedExamin) {
        return examinRepository.findById(id).map(existingExamin -> {
            existingExamin.setFkNumDossier(updatedExamin.getFkNumDossier());
            existingExamin.setFkIdEpreuve(updatedExamin.getFkIdEpreuve());
            existingExamin.setFkIdTestAnalyse(updatedExamin.getFkIdTestAnalyse());
            existingExamin.setResultat(updatedExamin.getResultat());
            Examin savedExamin = examinRepository.save(existingExamin);
            return ResponseEntity.ok(savedExamin);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamin(@PathVariable Long id) {
        if (examinRepository.existsById(id)) {
            examinRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
