package org.ensa.serviceanalyses.services;

import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.ensa.serviceanalyses.repositories.EpreuveRepository;
import org.ensa.serviceanalyses.repositories.TestAnalyseRepository;
import org.springframework.stereotype.Service;

@Service
public class TestAnalyseService {

    private final AnalyseRepository analyseRepository;
    private final TestAnalyseRepository testAnalyseRepository;

    public TestAnalyseService (AnalyseRepository analyseRepository,
                               TestAnalyseRepository testAnalyseRepository) {
        this.analyseRepository = analyseRepository;
        this.testAnalyseRepository = testAnalyseRepository;
    }

    public TestAnalyse createTestAnalyse(TestAnalyse testAnalyse) {
        if (analyseRepository.existsById(testAnalyse.getAnalyse().getId())) {
            return testAnalyseRepository.save(testAnalyse);
        } else {
            throw new IllegalArgumentException("Analyse not found for given ID");
        }
    }

    public TestAnalyse updateTestAnalyse(Long id, TestAnalyse updatedTestAnalyse) {
        TestAnalyse existingTestAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TestAnalyse not found for given ID"));

        if (!analyseRepository.existsById(updatedTestAnalyse.getAnalyse().getId())) {
            throw new IllegalArgumentException("Analyse not found for given ID");
        }

        existingTestAnalyse.setAnalyse(updatedTestAnalyse.getAnalyse());
        existingTestAnalyse.setNomTest(updatedTestAnalyse.getNomTest());
        existingTestAnalyse.setSousEpreuve(updatedTestAnalyse.getSousEpreuve());
        existingTestAnalyse.setIntervalMinDeReference(updatedTestAnalyse.getIntervalMinDeReference());
        existingTestAnalyse.setIntervalMaxDeReference(updatedTestAnalyse.getIntervalMaxDeReference());
        existingTestAnalyse.setUniteDeReference(updatedTestAnalyse.getUniteDeReference());
        existingTestAnalyse.setDetails(updatedTestAnalyse.getDetails());

        return testAnalyseRepository.save(existingTestAnalyse);
    }
}
