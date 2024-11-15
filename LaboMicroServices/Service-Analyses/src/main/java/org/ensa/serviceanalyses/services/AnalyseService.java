package org.ensa.serviceanalyses.services;

import jakarta.transaction.Transactional;
import org.ensa.serviceanalyses.clients.LaboratoireClient;
import org.ensa.serviceanalyses.entities.Analyse;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalyseService {

    private final AnalyseRepository analyseRepository;
    private final LaboratoireClient laboratoireClient;

    public AnalyseService (AnalyseRepository analyseRepository,
                           LaboratoireClient laboratoireClient){
        this.analyseRepository = analyseRepository;
        this.laboratoireClient = laboratoireClient;
    }

    @Transactional
    public Analyse createAnalyse(Analyse analyse) {
        if (laboratoireClient.existsById(analyse.getFkIdLaboratoire()).getBody() == Boolean.TRUE) {
            analyseRepository.save(analyse);
            return analyse;
        }
        else {
            throw new IllegalArgumentException("Laboratoire n'existe pas");
        }

    }

    @Transactional
    public Analyse updateAnalyse(Long id, Analyse updatedAnalyse) {
        if (laboratoireClient.existsById(updatedAnalyse.getFkIdLaboratoire()).getBody() == Boolean.FALSE) {
            throw new IllegalArgumentException("Laboratoire n'existe pas");
        }

        return analyseRepository.findById(id).map(existingAnalyse -> {
            laboratoireClient.existsById(updatedAnalyse.getFkIdLaboratoire());
            existingAnalyse.setDescription(updatedAnalyse.getDescription());
            existingAnalyse.setNom(updatedAnalyse.getNom());
            existingAnalyse.setFkIdLaboratoire(updatedAnalyse.getFkIdLaboratoire());
            return analyseRepository.save(existingAnalyse);
        }).orElseThrow(() -> new IllegalArgumentException("Analyse n'existe pas"));
    }
}
