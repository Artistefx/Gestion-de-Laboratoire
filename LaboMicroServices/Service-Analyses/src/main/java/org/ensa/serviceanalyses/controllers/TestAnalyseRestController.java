package org.ensa.serviceanalyses.controllers;

import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.ensa.serviceanalyses.repositories.TestAnalyseRepository;
import org.ensa.serviceanalyses.services.TestAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testAnalyse")
public class TestAnalyseRestController {


    private final TestAnalyseRepository testAnalyseRepository;
    private final TestAnalyseService testAnalyseService;

    public TestAnalyseRestController(TestAnalyseRepository testAnalyseRepository,
                                     TestAnalyseService testAnalyseService) {
        this.testAnalyseRepository = testAnalyseRepository;
        this.testAnalyseService = testAnalyseService;
    }


    @GetMapping
    public List<TestAnalyse> testAnalyseList() {
        return testAnalyseRepository.findAll();
    }

//    @GetMapping("/byAnalyse/{id}")
//    public List<TestAnalyse> getAllEpreuvesByIdAnalyse(@PathVariable("id") long id) {
//        return testAnalyseRepository.findAllByAnalyse_Id(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<TestAnalyse> getTestAnalyseById(@PathVariable Long id) {
        Optional<TestAnalyse> testAnalyse = testAnalyseRepository.findById(id);
        return testAnalyse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> testAnalyseExists(@PathVariable("id") long id){
        return ResponseEntity.ok(testAnalyseRepository.existsById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> addTestAnalyse(@RequestBody TestAnalyse newTestAnalyse) {
        try {
            TestAnalyse createdTestAnalyse = testAnalyseService.createTestAnalyse(newTestAnalyse);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTestAnalyse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the TestAnalyse");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<?> updateTestAnalyse(@PathVariable Long id, @RequestBody TestAnalyse updatedTestAnalyse) {
        try {
            TestAnalyse updatedTestAnalyseEntity = testAnalyseService.updateTestAnalyse(id, updatedTestAnalyse);
            return ResponseEntity.ok(updatedTestAnalyseEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the TestAnalyse");
        }
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<Void> deleteTestAnalyse(@PathVariable Long id) {
        if (testAnalyseRepository.existsById(id)) {
            testAnalyseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
