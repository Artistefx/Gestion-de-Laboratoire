package org.ensa.serviceanalyses.controllers;


import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.repositories.EpreuveRepository;
import org.ensa.serviceanalyses.services.EpreuveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/epreuve")
public class EpreuveRestController {

    private final EpreuveRepository epreuveRepository;
    private final EpreuveService epreuveService;

    @Autowired
    public EpreuveRestController(EpreuveRepository epreuveRepository,
                                 EpreuveService epreuveService) {
        this.epreuveRepository = epreuveRepository;
        this.epreuveService = epreuveService;
    }

    @GetMapping
    public List<Epreuve> getAllEpreuves() {
        return epreuveRepository.findAll();
    }

    @GetMapping("/byAnalyse/{id}")
    public List<Epreuve> getAllEpreuvesByIdAnalyse(@PathVariable("id") long id) {
        return epreuveRepository.findAllByAnalyse_Id(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Epreuve> getEpreuveById(@PathVariable Long id) {
        Optional<Epreuve> epreuve = epreuveRepository.findById(id);
        return epreuve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> epreuveExists(@PathVariable("id") long id){
        return ResponseEntity.ok(epreuveRepository.existsById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEpreuve(@RequestBody Epreuve newEpreuve) {
        try {
            Epreuve createdEpreuve = epreuveService.createEpreuve(newEpreuve);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEpreuve);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the epreuve");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpreuve(@PathVariable Long id, @RequestBody Epreuve updatedEpreuve) {
        try {
            Epreuve updEpreuve = epreuveService.updateEpreuve(id, updatedEpreuve);
            return ResponseEntity.ok(updEpreuve);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the epreuve");
        }
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