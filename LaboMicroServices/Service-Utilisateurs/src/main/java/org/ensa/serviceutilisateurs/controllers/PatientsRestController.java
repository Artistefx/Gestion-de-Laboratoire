package org.ensa.serviceutilisateurs.controllers;



import feign.FeignException;
import org.ensa.serviceutilisateurs.entities.Patients;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.PatientRepository;
import org.ensa.serviceutilisateurs.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientsRestController {


    private final PatientRepository patientRepository;
    private final PatientService patientService;


    PatientsRestController(PatientRepository patientRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }


    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody Patients Patients) {
        try{
            Patients savedPatient = patientService.createPatient(Patients);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(/*"Erreur: Une erreur est survenue lors de la creation du patient."*/e.getMessage());
        }
    }


    @GetMapping
    public List<Patients> getAllPatients() {
        return patientRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Patients> getPatientById(@PathVariable Long id) {
        Optional<Patients> patient = patientRepository.findById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> patientExists(@PathVariable Long id) {
        if (patientRepository.existsById(id)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patients updatedPatient) {
        try{
            Patients updatesPatient = patientService.updatePatient(id, updatedPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatesPatient);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratoire n'existe pas");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur: Une erreur est survenue lors de la mise a jour du patient.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatient(@PathVariable Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
