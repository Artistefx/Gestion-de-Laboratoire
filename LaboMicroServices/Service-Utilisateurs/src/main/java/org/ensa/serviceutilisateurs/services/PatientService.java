package org.ensa.serviceutilisateurs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.ensa.serviceutilisateurs.clients.LaboratoireClient;
import org.ensa.serviceutilisateurs.entities.Patients;
import org.ensa.serviceutilisateurs.producers.NotificationProducer;
import org.ensa.serviceutilisateurs.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final LaboratoireClient laboratoireClient;
    private final NotificationProducer notificationProducer;

    public PatientService(PatientRepository patientRepository, LaboratoireClient laboratoireClient, NotificationProducer notificationProducer) {
        this.patientRepository = patientRepository;
        this.laboratoireClient = laboratoireClient;
        this.notificationProducer = notificationProducer;
    }


    @Transactional
    public Patients createPatient(Patients patient) throws JsonProcessingException {
        laboratoireClient.getLaboratoireById(patient.getFkIdLaboratoire());
        /*notificationProducer.sendEmail(patient.getEmail(), "Account Created", "Test message");*/
        /*notificationProducer.sendSms(patient.getNumTel() , "Test message");*/
        return patientRepository.save(patient);
    }

    @Transactional
    public Patients updatePatient(Long id, Patients updatedPatient) {
        return patientRepository.findById(id).map(existingPatient -> {

            laboratoireClient.getLaboratoireById(updatedPatient.getFkIdLaboratoire());

            existingPatient.setNomComplet(updatedPatient.getNomComplet());
            existingPatient.setDateNaissance(updatedPatient.getDateNaissance());
            existingPatient.setLieuDeNaissance(updatedPatient.getLieuDeNaissance());
            existingPatient.setSexe(updatedPatient.getSexe());
            existingPatient.setNumPieceIdentite(updatedPatient.getNumPieceIdentite());
            existingPatient.setAdresse(updatedPatient.getAdresse());
            existingPatient.setNumTel(updatedPatient.getNumTel());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setVisible_pour(updatedPatient.getVisible_pour());

            return patientRepository.save(existingPatient);
        }).orElseThrow(() -> new IllegalArgumentException("Patient n'existe pas avec cette ID"));
    }

    public String verifyPatient(Long id) throws JsonProcessingException {
       Patients patients = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient n'existe pas avec cette ID"));
       String code = String.valueOf((int) (Math.random() * 10000));
       System.out.println(code);
       Date dateExpiration = new Date(System.currentTimeMillis() + 60000);
        HashMap<String, String> variables = new HashMap<>();
        variables.put("nom", patients.getNomComplet());
        variables.put("codeVerification", code);
        variables.put("dateExpiration", dateExpiration.toString());
       notificationProducer.sendEmail(patients.getEmail(), "Code de Verification", "code-verif.html", variables);
       return code;
    }

    public Optional<Patients> getPatientByemail(String email) {
        return patientRepository.findByEmail(email);
    }
}
