package org.ensa.serviceutilisateurs.services;

import jakarta.transaction.Transactional;
import org.ensa.serviceutilisateurs.clients.LaboratoireClient;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilisateursService {

    private final UtilisateursRepository utilisateursRepository;
    private final LaboratoireClient laboratoireClient;

    public UtilisateursService(UtilisateursRepository utilisateursRepository,
                               LaboratoireClient laboratoireClient) {
        this.utilisateursRepository = utilisateursRepository;
        this.laboratoireClient = laboratoireClient;
    }

    @Transactional
    public Utilisateurs createUtilisateur(Utilisateurs utilisateur) {

        if (utilisateursRepository.existsById(utilisateur.getEmail())){
            throw new IllegalArgumentException("Utilisateur deja existant");
        }

        laboratoireClient.getLaboratoireById(utilisateur.getFkIdLaboratoire());

        return utilisateursRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateurs updateContactLaboratoire(String email, Utilisateurs updatedutilisateurs) {
        return utilisateursRepository.findById(email).map(existingUtilisateur -> {

            laboratoireClient.getLaboratoireById(updatedutilisateurs.getFkIdLaboratoire());

            existingUtilisateur.setNomComplet(updatedutilisateurs.getNomComplet());
            existingUtilisateur.setProfession(updatedutilisateurs.getProfession());
            existingUtilisateur.setRole(updatedutilisateurs.getRole());
            existingUtilisateur.setSignature(updatedutilisateurs.getSignature());
            existingUtilisateur.setFkIdLaboratoire(updatedutilisateurs.getFkIdLaboratoire());
            existingUtilisateur.setNumTel(updatedutilisateurs.getNumTel());


            return utilisateursRepository.save(existingUtilisateur);
        }).orElseThrow(() -> new IllegalArgumentException("Utilisateur n'existe pas avec cet Email"));
    }
}
