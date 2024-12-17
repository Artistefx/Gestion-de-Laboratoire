package org.ensa.serviceanalyses.controllers;


import feign.FeignException;
import org.ensa.serviceanalyses.entities.Analyse;
import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.ensa.serviceanalyses.services.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Analyse")
public class AnalyseRestController {


    private final AnalyseRepository analyseRepository;
    private final AnalyseService analyseService;

    public AnalyseRestController(AnalyseRepository analyseRepository,
                                 AnalyseService analyseService) {
        this.analyseRepository = analyseRepository;
        this.analyseService = analyseService;
    }


    @PostMapping
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> createAnalyse(@RequestBody Analyse analyse) {
        try{
            analyseService.createAnalyse(analyse);
            return ResponseEntity.status(HttpStatus.CREATED).body(analyse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inconnue");
        }
    }


    @GetMapping
    public List<Analyse> getAllAnalyses() {
        return analyseRepository.findAll();
    }

    @GetMapping("/byLaboratoire/{id}")
    public List<Analyse> getAllEpreuvesByIdAnalyse(@PathVariable("id") long id) {
        return analyseRepository.findAllByFkIdLaboratoire(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analyse> getAnalyseById(@PathVariable Long id) {
        Optional<Analyse> analyse = analyseRepository.findById(id);
        return analyse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> updateAnalyse(@PathVariable Long id, @RequestBody Analyse updatedAnalyse) {
        try{
            Analyse savedAnalyse =  analyseService.updateAnalyse(id , updatedAnalyse);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAnalyse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur: Une erreur est survenue lors de la mise a jour de l'analyse.");
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<Void> deleteAnalyse(@PathVariable Long id) {
        if (analyseRepository.existsById(id)) {
            analyseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}