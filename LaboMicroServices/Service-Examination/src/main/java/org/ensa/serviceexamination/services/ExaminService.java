package org.ensa.serviceexamination.services;


import jakarta.transaction.Transactional;
import org.ensa.serviceexamination.clients.TestAnalyseClient;
import org.ensa.serviceexamination.clients.EpreuveClient;
import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.entities.Examin;
import org.ensa.serviceexamination.repositories.ExaminRepository;
import org.springframework.stereotype.Service;

@Service
public class ExaminService {

    private final ExaminRepository examinRepository;
    private final EpreuveClient epreuveClient;
    private final TestAnalyseClient testAnalyseClient;

    public ExaminService(ExaminRepository examinRepository,
                         EpreuveClient epreuveClient,
                         TestAnalyseClient testAnalyseClient){
        this.examinRepository = examinRepository;
        this.epreuveClient = epreuveClient;
        this.testAnalyseClient = testAnalyseClient;
    }

    @Transactional
    public boolean CreateOrUpdateExamin (Examin examin){
         if(Boolean.FALSE.equals(epreuveClient.epreuveExists(examin.getFkIdEpreuve()).getBody())){
             throw new IllegalArgumentException("Epreuve n'existe pas");
         }

        if(Boolean.FALSE.equals(testAnalyseClient.testAnalyseExists(examin.getFkIdTestAnalyse()).getBody())){
            throw new IllegalArgumentException("Test Analyse n'existe pas");
        }

        examinRepository.save(examin);

        return true;
    }

}
