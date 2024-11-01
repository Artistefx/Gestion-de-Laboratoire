package org.ensa.serviceanalyses.controllers;


import org.ensa.serviceanalyses.entities.Analyse;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Analyse")
public class AnalyseRestController {

    @Autowired
    private AnalyseRepository analyseRepository;

    public AnalyseRestController(AnalyseRepository analyseRepository) {
        this.analyseRepository = analyseRepository;
    }


    @PostMapping
    public Analyse createAnalyse(@RequestBody Analyse analyse) {
        return analyseRepository.save(analyse);
    }


    @GetMapping
    public List<Analyse> getAllAnalyses() {
        return analyseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analyse> getAnalyseById(@PathVariable Long id) {
        Optional<Analyse> analyse = analyseRepository.findById(id);
        return analyse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Analyse> updateAnalyse(@PathVariable Long id, @RequestBody Analyse updatedAnalyse) {
        return analyseRepository.findById(id).map(existingAnalyse -> {
            existingAnalyse.setFkIdLaboratoire(updatedAnalyse.getFkIdLaboratoire());
            existingAnalyse.setNom(updatedAnalyse.getNom());
            existingAnalyse.setDescription(updatedAnalyse.getDescription());
            Analyse savedAnalyse = analyseRepository.save(existingAnalyse);
            return ResponseEntity.ok(savedAnalyse);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalyse(@PathVariable Long id) {
        if (analyseRepository.existsById(id)) {
            analyseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}