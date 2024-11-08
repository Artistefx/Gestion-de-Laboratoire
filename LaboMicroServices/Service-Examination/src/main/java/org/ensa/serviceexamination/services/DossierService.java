package org.ensa.serviceexamination.services;


import jakarta.transaction.Transactional;
import org.ensa.serviceexamination.clients.PatientClient;
import org.ensa.serviceexamination.clients.UtilisateurClient;
import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.repositories.DossierRepository;
import org.springframework.stereotype.Service;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final UtilisateurClient utilisateurClient;
    private final PatientClient patientClient;

    public DossierService (DossierRepository dossierRepository,
                           UtilisateurClient utilisateurClient,
                           PatientClient patientClient){
        this.dossierRepository = dossierRepository;
        this.utilisateurClient = utilisateurClient;
        this.patientClient = patientClient;
    }

    @Transactional
    public boolean CreateOrUpdateDossier (Dossier dossier){
         if(Boolean.FALSE.equals(utilisateurClient.utlisateurExists(dossier.getFkEmailUtilisateur()).getBody())){
             throw new IllegalArgumentException("Utilisateur n'existe pas");
         }

        if(Boolean.FALSE.equals(patientClient.patientExists(dossier.getFkIdPatient()).getBody())){
            throw new IllegalArgumentException("Patient n'existe pas");
        }

        dossierRepository.save(dossier);

        return true;
    }

}
