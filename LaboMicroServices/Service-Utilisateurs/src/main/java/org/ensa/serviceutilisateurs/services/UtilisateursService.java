package org.ensa.serviceutilisateurs.services;

import jakarta.transaction.Transactional;
import org.ensa.serviceutilisateurs.clients.LaboratoireClient;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateursService implements UserDetailsService {

    private final UtilisateursRepository utilisateursRepository;
    private final LaboratoireClient laboratoireClient;
    private final PasswordEncoder passwordEncoder;

    public UtilisateursService(UtilisateursRepository utilisateursRepository,
                               LaboratoireClient laboratoireClient,
                               PasswordEncoder passwordEncoder) {
        this.utilisateursRepository = utilisateursRepository;
        this.laboratoireClient = laboratoireClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Utilisateurs createUtilisateur(Utilisateurs utilisateur) {

        if (utilisateursRepository.existsById(utilisateur.getEmail())){
            throw new IllegalArgumentException("Utilisateur deja existant");
        }

        laboratoireClient.getLaboratoireById(utilisateur.getFkIdLaboratoire());
        String encodedPassword = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encodedPassword);
        return utilisateursRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateurs updateContactLaboratoire(String email, Utilisateurs updatedutilisateurs) {
        return utilisateursRepository.findById(email).map(existingUtilisateur -> {

            laboratoireClient.getLaboratoireById(updatedutilisateurs.getFkIdLaboratoire());

            String encodedPassword = passwordEncoder.encode(updatedutilisateurs.getPassword());

            existingUtilisateur.setNomComplet(updatedutilisateurs.getNomComplet());
            existingUtilisateur.setPassword(encodedPassword);
            existingUtilisateur.setProfession(updatedutilisateurs.getProfession());
            existingUtilisateur.setRole(updatedutilisateurs.getRole());
            existingUtilisateur.setSignature(updatedutilisateurs.getSignature());
            existingUtilisateur.setFkIdLaboratoire(updatedutilisateurs.getFkIdLaboratoire());
            existingUtilisateur.setNumTel(updatedutilisateurs.getNumTel());


            return utilisateursRepository.save(existingUtilisateur);
        }).orElseThrow(() -> new IllegalArgumentException("Utilisateur n'existe pas avec cet Email"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateurs utilisateur = findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .roles(utilisateur.getRole())
                .build();
    }

    private Optional<Utilisateurs> findUserByUsername(String email) {
        return utilisateursRepository.findById(email);
    }
}
