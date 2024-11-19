package org.ensa.serviceexamination.controllers;



import org.ensa.serviceexamination.entities.Examin;
import org.ensa.serviceexamination.repositories.ExaminRepository;
import org.ensa.serviceexamination.services.ExaminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Examin")
public class ExaminRestController {


    private final ExaminRepository examinRepository;
    private final ExaminService examinService;


    public ExaminRestController(ExaminRepository examinRepository,
                                ExaminService examinService) {
        this.examinRepository = examinRepository;
        this.examinService = examinService;
    }

    @PostMapping
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> createExamin(@RequestBody Examin examin) {
        try{
            examinService.CreateOrUpdateExamin(examin);
            return new ResponseEntity<>(examin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public List<Examin> getAllExamin() {
        return examinRepository.findAll();
    }

    @GetMapping("/byDossier/{numDossier}")
    public List<Examin> getAllExaminByDossier(@PathVariable Long numDossier) {
        return examinRepository.findAllByDossier_NumDossier(numDossier);
    }

    @GetMapping("/byEpreuve/{id}")
    public List<Examin> getAllExaminByEpreuve(@PathVariable Long id) {
        return examinRepository.findAllByFkIdEpreuve(id);
    }

    @GetMapping("/byTestAnalyse/{id}")
    public List<Examin> getAllExaminByTestAnalyse(@PathVariable Long id) {
        return examinRepository.findAllByFkIdTestAnalyse(id);
    }

    @GetMapping("/byNumDossier/{numDossier}")
    public List<Examin> getAllExamin(@PathVariable Long numDossier) {
        return examinRepository.findAllByDossier_NumDossier(numDossier);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Examin> getExaminById(@PathVariable Long id) {
        Optional<Examin> examin = examinRepository.findById(id);
        return examin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> updateExamin(@PathVariable Long id, @RequestBody Examin updatedExamin) {

        try{
            examinRepository.findById(id).map(existingExamin -> {
                existingExamin.setDossier(updatedExamin.getDossier()); // update the Dossier object
                existingExamin.setFkIdEpreuve(updatedExamin.getFkIdEpreuve());
                existingExamin.setFkIdTestAnalyse(updatedExamin.getFkIdTestAnalyse());
                existingExamin.setResultat(updatedExamin.getResultat());
                examinService.CreateOrUpdateExamin(existingExamin);
                return new ResponseEntity<>(existingExamin, HttpStatus.CREATED);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Probleme survenu lors de la mise a jour", HttpStatus.INTERNAL_SERVER_ERROR);
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
