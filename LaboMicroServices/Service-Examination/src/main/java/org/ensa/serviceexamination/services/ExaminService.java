package org.ensa.serviceexamination.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.ensa.serviceexamination.DTO.EpreuveDTO;
import org.ensa.serviceexamination.DTO.PatientDTO;
import org.ensa.serviceexamination.clients.PatientClient;
import org.ensa.serviceexamination.clients.TestAnalyseClient;
import org.ensa.serviceexamination.clients.EpreuveClient;
import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.entities.Examin;
import org.ensa.serviceexamination.producers.NotificationProducer;
import org.ensa.serviceexamination.repositories.DossierRepository;
import org.ensa.serviceexamination.repositories.ExaminRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ExaminService {

    private final ExaminRepository examinRepository;
    private final DossierRepository dossierRepository;
    private final EpreuveClient epreuveClient;
    private final TestAnalyseClient testAnalyseClient;
    private final PatientClient patientClient;
    private final NotificationProducer notificationProducer;

    public ExaminService(ExaminRepository examinRepository, DossierRepository dossierRepository,
                         EpreuveClient epreuveClient,
                         TestAnalyseClient testAnalyseClient, PatientClient patientClient, NotificationProducer notificationProducer){
        this.examinRepository = examinRepository;
        this.dossierRepository = dossierRepository;
        this.epreuveClient = epreuveClient;
        this.testAnalyseClient = testAnalyseClient;
        this.patientClient = patientClient;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public void CreateOrUpdateExamin (Examin examin) throws JsonProcessingException {
         if(Boolean.FALSE.equals(epreuveClient.epreuveExists(examin.getFkIdEpreuve()).getBody())){
             throw new IllegalArgumentException("Epreuve n'existe pas");
         }

        if(Boolean.FALSE.equals(testAnalyseClient.testAnalyseExists(examin.getFkIdTestAnalyse()).getBody())){
            throw new IllegalArgumentException("Test Analyse n'existe pas");
        }

        examinRepository.save(examin);
        this.sendMail(examin.getDossier().getNumDossier(), examin.getUniqueId(), examin.getFkIdEpreuve());
    }

    public void sendMail (Long idDossier , String CodeExamin , Long idEpreuve) throws JsonProcessingException {
        Optional<Dossier> dossier = dossierRepository.findById(idDossier);

        PatientDTO patientDTO = patientClient.getPatientById(dossier.get().getFkIdPatient()).getBody();

        EpreuveDTO epreuveDTO = epreuveClient.getEpreuveById(idEpreuve).getBody();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("nom" , patientDTO.getNomComplet());
        variables.put("typeAnalyse", epreuveDTO.getNom());
        variables.put("codeAnalyse", CodeExamin);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        variables.put("dateAnalyse", formattedDateTime);

        notificationProducer.sendEmail(patientDTO.getEmail(), "Analyse effectuee", "analyse-effectuee.html", variables);
    }

}
