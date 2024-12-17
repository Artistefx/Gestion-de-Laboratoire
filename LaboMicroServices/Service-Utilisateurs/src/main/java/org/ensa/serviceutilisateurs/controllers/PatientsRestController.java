package org.ensa.serviceutilisateurs.controllers;



import org.ensa.serviceutilisateurs.entities.Patients;
import org.ensa.serviceutilisateurs.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/patients")
public class PatientsRestController {


    private final PatientRepository patientRepository;


    PatientsRestController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @PostMapping
    public ResponseEntity<Patients> addPatient(@RequestBody Patients Patients) {
        Patients savedPatient = patientRepository.save(Patients);
        return ResponseEntity.ok(savedPatient);
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
    public ResponseEntity<Patients> updatePatient(@PathVariable Long id, @RequestBody Patients updatedPatient) {
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setNomComplet(updatedPatient.getNomComplet());
            existingPatient.setDateNaissance(updatedPatient.getDateNaissance());
            existingPatient.setLieuDeNaissance(updatedPatient.getLieuDeNaissance());
            existingPatient.setSexe(updatedPatient.getSexe());
            existingPatient.setNumPieceIdentite(updatedPatient.getNumPieceIdentite());
            existingPatient.setAdresse(updatedPatient.getAdresse());
            existingPatient.setNumTel(updatedPatient.getNumTel());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setVisible_pour(updatedPatient.getVisible_pour());
            patientRepository.save(existingPatient);
            return ResponseEntity.ok(existingPatient);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
