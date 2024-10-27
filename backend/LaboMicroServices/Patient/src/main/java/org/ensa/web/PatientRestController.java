package org.ensa.web;

import org.ensa.entities.patient;
import org.ensa.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientRestController {

    @Autowired
    private PatientRepository patientRepository;

    // Ajouter un patient
    @PostMapping
    public ResponseEntity<patient> addPatient(@RequestBody patient patient) {
        patient savedPatient = patientRepository.save(patient);
        return ResponseEntity.ok(savedPatient);
    }

    // Récupérer tous les patients
    @GetMapping
    public List<patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Récupérer un patient par ID
    @GetMapping("/{id}")
    public ResponseEntity<patient> getPatientById(@PathVariable Long id) {
        Optional<patient> patient = patientRepository.findById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un patient
    @PutMapping("/{id}")
    public ResponseEntity<patient> updatePatient(@PathVariable Long id, @RequestBody patient updatedPatient) {
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

    // Supprimer un patient
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
