package org.ensa.serviceutilisateurs.UtilisateursTest;

import feign.FeignException;
import org.ensa.serviceutilisateurs.controllers.UtilisateursRestController;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.ensa.serviceutilisateurs.services.UtilisateursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UtilisateursTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @Mock
    private UtilisateursService utilisateursService;

    @InjectMocks
    private UtilisateursRestController utilisateursRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUtilisateursList() {
        List<Utilisateurs> utilisateurs = Arrays.asList(new Utilisateurs(), new Utilisateurs());
        when(utilisateursRepository.findAll()).thenReturn(utilisateurs);

        List<Utilisateurs> result = utilisateursRestController.utilisateursList();
        assertEquals(2, result.size());
        verify(utilisateursRepository, times(1)).findAll();
    }

    @Test
    void testGetUtilisateurByEmail_UserExists() {
        Utilisateurs utilisateur = new Utilisateurs();
        when(utilisateursRepository.findById("test@example.com")).thenReturn(Optional.of(utilisateur));

        ResponseEntity<Utilisateurs> response = utilisateursRestController.getUtilisateurByEmail("test@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateur, response.getBody());
        verify(utilisateursRepository, times(1)).findById("test@example.com");
    }

    @Test
    void testGetUtilisateurByEmail_UserDoesNotExist() {
        when(utilisateursRepository.findById("nonexistent@example.com")).thenReturn(Optional.empty());

        ResponseEntity<Utilisateurs> response = utilisateursRestController.getUtilisateurByEmail("nonexistent@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(utilisateursRepository, times(1)).findById("nonexistent@example.com");
    }

    @Test
    void testGetAllByIdLabo() {
        List<Utilisateurs> utilisateurs = Arrays.asList(new Utilisateurs(), new Utilisateurs());
        when(utilisateursRepository.findAllByFkIdLaboratoire(1L)).thenReturn(utilisateurs);

        ResponseEntity<List<Utilisateurs>> response = utilisateursRestController.getAllByIdLabo(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateurs, response.getBody());
        verify(utilisateursRepository, times(1)).findAllByFkIdLaboratoire(1L);
    }

    @Test
    void testUtilisateurExists_UserExists() {
        when(utilisateursRepository.existsById("test@example.com")).thenReturn(true);

        ResponseEntity<Boolean> response = utilisateursRestController.utilisateurExists("test@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(utilisateursRepository, times(1)).existsById("test@example.com");
    }

    @Test
    void testUtilisateurExists_UserDoesNotExist() {
        when(utilisateursRepository.existsById("nonexistent@example.com")).thenReturn(false);

        ResponseEntity<Boolean> response = utilisateursRestController.utilisateurExists("nonexistent@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(utilisateursRepository, times(1)).existsById("nonexistent@example.com");
    }

    @Test
    void testAddUtilisateur_Success() {
        Utilisateurs utilisateur = new Utilisateurs();
        when(utilisateursService.createUtilisateur(any(Utilisateurs.class))).thenReturn(utilisateur);

        ResponseEntity<?> response = utilisateursRestController.addUtilisateur(utilisateur);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(utilisateur, response.getBody());
        verify(utilisateursService, times(1)).createUtilisateur(any(Utilisateurs.class));
    }

    @Test
    void testAddUtilisateur_BadRequest() {
        when(utilisateursService.createUtilisateur(any(Utilisateurs.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<?> response = utilisateursRestController.addUtilisateur(new Utilisateurs());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody());
    }

    @Test
    void testUpdateUtilisateur_Success() {
        Utilisateurs utilisateur = new Utilisateurs();
        when(utilisateursService.updateContactLaboratoire(eq("test@example.com"), any(Utilisateurs.class))).thenReturn(utilisateur);

        ResponseEntity<?> response = utilisateursRestController.updateUtilisateur("test@example.com", utilisateur);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(utilisateur, response.getBody());
        verify(utilisateursService, times(1)).updateContactLaboratoire(eq("test@example.com"), any(Utilisateurs.class));
    }

    @Test
    void testDeleteUtilisateur_UserExists() {
        when(utilisateursRepository.existsById("test@example.com")).thenReturn(true);

        ResponseEntity<Void> response = utilisateursRestController.deleteUtilisateur("test@example.com");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(utilisateursRepository, times(1)).deleteById("test@example.com");
    }

    @Test
    void testDeleteUtilisateur_UserDoesNotExist() {
        when(utilisateursRepository.existsById("nonexistent@example.com")).thenReturn(false);

        ResponseEntity<Void> response = utilisateursRestController.deleteUtilisateur("nonexistent@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(utilisateursRepository, never()).deleteById("nonexistent@example.com");
    }
}
