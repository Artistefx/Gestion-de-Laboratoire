package org.ensa.serviceanalyses.services;

import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.ensa.serviceanalyses.repositories.EpreuveRepository;
import org.ensa.serviceanalyses.repositories.TestAnalyseRepository;
import org.springframework.stereotype.Service;

@Service
public class TestAnalyseService {


    private final TestAnalyseRepository testAnalyseRepository;

    public TestAnalyseService (
                               TestAnalyseRepository testAnalyseRepository) {

        this.testAnalyseRepository = testAnalyseRepository;
    }

    public TestAnalyse createTestAnalyse(TestAnalyse testAnalyse) {

            return testAnalyseRepository.save(testAnalyse);

    }

    public TestAnalyse updateTestAnalyse(Long id, TestAnalyse updatedTestAnalyse) {
        TestAnalyse existingTestAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TestAnalyse not found for given ID"));





        existingTestAnalyse.setNomTest(updatedTestAnalyse.getNomTest());
        existingTestAnalyse.setSousEpreuve(updatedTestAnalyse.getSousEpreuve());
        existingTestAnalyse.setIntervalMinDeReference(updatedTestAnalyse.getIntervalMinDeReference());
        existingTestAnalyse.setIntervalMaxDeReference(updatedTestAnalyse.getIntervalMaxDeReference());
        existingTestAnalyse.setUniteDeReference(updatedTestAnalyse.getUniteDeReference());
        existingTestAnalyse.setDetails(updatedTestAnalyse.getDetails());

        return testAnalyseRepository.save(existingTestAnalyse);
    }
}
