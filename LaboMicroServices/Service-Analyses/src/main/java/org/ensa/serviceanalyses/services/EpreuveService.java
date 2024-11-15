package org.ensa.serviceanalyses.services;


import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.ensa.serviceanalyses.repositories.EpreuveRepository;
import org.springframework.stereotype.Service;

@Service
public class EpreuveService {

    private final EpreuveRepository epreuveRepository;
    private final AnalyseRepository analyseRepository;

    public EpreuveService (EpreuveRepository epreuveRepository,
                           AnalyseRepository analyseRepository) {
        this.epreuveRepository = epreuveRepository;
        this.analyseRepository = analyseRepository;
    }

    public Epreuve createEpreuve(Epreuve epreuve) {
        if (!analyseRepository.existsById(epreuve.getAnalyse().getId())) {
            throw new IllegalArgumentException("Analyse non trouvée");
        }
        return epreuveRepository.save(epreuve);
    }

    public Epreuve updateEpreuve(Long id, Epreuve epreuve) {
        return epreuveRepository.findById(id).map(existingEpreuve -> {
            if (!analyseRepository.existsById(epreuve.getAnalyse().getId())) {
                throw new IllegalArgumentException("Analyse non trouvée");
            }

            existingEpreuve.setNom(epreuve.getNom());
            existingEpreuve.setAnalyse(epreuve.getAnalyse());
            return epreuveRepository.save(existingEpreuve);
        }).orElseThrow(() -> new IllegalArgumentException("Épreuve non trouvée"));
    }
}
