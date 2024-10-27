package org.ensa.testanalyse.web;

import org.ensa.testanalyse.entities.testAnalyse;
import org.ensa.testanalyse.repository.testAnalyseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testAnalyse")
public class testAnalyseRestController {

    @Autowired
    private testAnalyseRepository testAnalyseRepository;

    public testAnalyseRestController(testAnalyseRepository testAnalyseRepository) {
        this.testAnalyseRepository = testAnalyseRepository;
    }


    @GetMapping
    public List<testAnalyse> testAnalyseList() {
        return testAnalyseRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<testAnalyse> getTestAnalyseById(@PathVariable Long id) {
        Optional<testAnalyse> testAnalyse = testAnalyseRepository.findById(id);
        return testAnalyse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public testAnalyse addTestAnalyse(@RequestBody testAnalyse newTestAnalyse) {
        return testAnalyseRepository.save(newTestAnalyse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<testAnalyse> updateTestAnalyse(@PathVariable Long id, @RequestBody testAnalyse updatedTestAnalyse) {
        return testAnalyseRepository.findById(id).map(existingTestAnalyse -> {
            existingTestAnalyse.setFkIdAnalyse(updatedTestAnalyse.getFkIdAnalyse());
            existingTestAnalyse.setNomTest(updatedTestAnalyse.getNomTest());
            existingTestAnalyse.setSousEpreuve(updatedTestAnalyse.getSousEpreuve());
            existingTestAnalyse.setIntervalMinDeReference(updatedTestAnalyse.getIntervalMinDeReference());
            existingTestAnalyse.setIntervalMaxDeReference(updatedTestAnalyse.getIntervalMaxDeReference());
            existingTestAnalyse.setUniteDeReference(updatedTestAnalyse.getUniteDeReference());
            existingTestAnalyse.setDetails(updatedTestAnalyse.getDetails());
            testAnalyseRepository.save(existingTestAnalyse);
            return ResponseEntity.ok(existingTestAnalyse);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a testAnalyse entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestAnalyse(@PathVariable Long id) {
        if (testAnalyseRepository.existsById(id)) {
            testAnalyseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
