package org.ensa.serviceexamination.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.ensa.serviceexamination.DTO.PatientDTO;
import org.ensa.serviceexamination.clients.PatientClient;
import org.ensa.serviceexamination.clients.UtilisateurClient;
import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.producers.NotificationProducer;
import org.ensa.serviceexamination.repositories.DossierRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final UtilisateurClient utilisateurClient;
    private final PatientClient patientClient;
    private final NotificationProducer notificationProducer;

    public DossierService (DossierRepository dossierRepository,
                           UtilisateurClient utilisateurClient,
                           PatientClient patientClient, NotificationProducer notificationProducer){
        this.dossierRepository = dossierRepository;
        this.utilisateurClient = utilisateurClient;
        this.patientClient = patientClient;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public Dossier CreateOrUpdateDossier (Dossier dossier) throws JsonProcessingException {
         if(Boolean.FALSE.equals(utilisateurClient.utilisateurExists(dossier.getFkEmailUtilisateur()).getBody())){
             throw new IllegalArgumentException("Utilisateur n'existe pas");
         }

        if(Boolean.FALSE.equals(patientClient.patientExists(dossier.getFkIdPatient()).getBody())){
            System.out.println(dossier.getFkIdPatient());
            System.out.println(patientClient.patientExists(dossier.getFkIdPatient()).getBody());
            throw new IllegalArgumentException("Patient n'existe pas");
        }
        dossierRepository.save(dossier);
        this.sendMail(dossier.getFkIdPatient(), dossier.getUniqueId());
        return dossier;
    }

    public Optional<Dossier> getDossierByUniqueId(String uid) {
        return dossierRepository.findDossierByUniqueId(uid);
    }

    public void sendMail (Long id , String CodeDossier) throws JsonProcessingException {
        PatientDTO patientDTO = patientClient.getPatientById(id).getBody();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("nom" , patientDTO.getNomComplet());
        variables.put("codeDossier", CodeDossier);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        variables.put("dateCreation", formattedDateTime);

        notificationProducer.sendEmail(patientDTO.getEmail(), "Dossier cree", "dossier-cree.html", variables);
    }

    public void recoverDossier(String email) throws JsonProcessingException {
        PatientDTO patientDTO = patientClient.getPatientByEmail(email).getBody();
        Dossier dossier = dossierRepository.findDossierByFkIdPatient(patientDTO.getIdPatient()).orElseThrow(() -> new IllegalArgumentException("Dossier n'existe pas"));

        HashMap<String, String> variables = new HashMap<>();
        variables.put("nom" , patientDTO.getNomComplet());
        variables.put("codeDossier", dossier.getUniqueId());

        notificationProducer.sendEmail(patientDTO.getEmail(), "Recuperation de Dossier", "dossier-recupere.html", variables);
    }
}