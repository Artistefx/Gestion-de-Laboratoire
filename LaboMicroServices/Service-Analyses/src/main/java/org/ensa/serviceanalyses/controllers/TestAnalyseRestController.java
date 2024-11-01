package org.ensa.serviceanalyses.controllers;

import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.ensa.serviceanalyses.repositories.TestAnalyseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testAnalyse")
public class TestAnalyseRestController {

    @Autowired
    private TestAnalyseRepository testAnalyseRepository;

    public TestAnalyseRestController(TestAnalyseRepository testAnalyseRepository) {
        this.testAnalyseRepository = testAnalyseRepository;
    }


    @GetMapping
    public List<TestAnalyse> testAnalyseList() {
        return testAnalyseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestAnalyse> getTestAnalyseById(@PathVariable Long id) {
        Optional<TestAnalyse> testAnalyse = testAnalyseRepository.findById(id);
        return testAnalyse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public TestAnalyse addTestAnalyse(@RequestBody TestAnalyse newTestAnalyse) {
        return testAnalyseRepository.save(newTestAnalyse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TestAnalyse> updateTestAnalyse(@PathVariable Long id, @RequestBody TestAnalyse updatedTestAnalyse) {
        return testAnalyseRepository.findById(id).map(existingTestAnalyse -> {
            existingTestAnalyse.setAnalyse(updatedTestAnalyse.getAnalyse());
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



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestAnalyse(@PathVariable Long id) {
        if (testAnalyseRepository.existsById(id)) {
            testAnalyseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
