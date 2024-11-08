package org.ensa.serviceutilisateurs.UtilisateursTest;

import org.ensa.serviceutilisateurs.DTOs.LaboratoireDTO;
import org.ensa.serviceutilisateurs.clients.LaboratoireClient;
import org.ensa.serviceutilisateurs.entities.Utilisateurs;
import org.ensa.serviceutilisateurs.repositories.UtilisateursRepository;
import org.ensa.serviceutilisateurs.services.UtilisateursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UtilisateursServiceCreateTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @Mock
    private LaboratoireClient laboratoireClient;

    @InjectMocks
    private UtilisateursService utilisateursService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUtilisateur_Successful() {
        // Arrange
        Utilisateurs newUtilisateur = new Utilisateurs();
        newUtilisateur.setEmail("test@example.com");
        newUtilisateur.setFkIdLaboratoire(1L);

        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(new LaboratoireDTO()); // Mock laboratoire exists
        when(utilisateursRepository.existsById("test@example.com")).thenReturn(false);
        when(utilisateursRepository.save(any(Utilisateurs.class))).thenReturn(newUtilisateur);

        // Act
        Utilisateurs savedUser = utilisateursService.createUtilisateur(newUtilisateur);

        // Assert
        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
        verify(utilisateursRepository, times(1)).save(any(Utilisateurs.class));
    }

    @Test
    public void testCreateUtilisateur_LaboratoireDoesNotExist() {
        // Arrange
        Utilisateurs newUtilisateur = new Utilisateurs();
        newUtilisateur.setEmail("test@example.com");
        newUtilisateur.setFkIdLaboratoire(1L);

        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(null); // Mock laboratoire does not exist

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            utilisateursService.createUtilisateur(newUtilisateur);
        });
        assertEquals("Laboratoire non existant", exception.getMessage());
        verify(utilisateursRepository, never()).save(any(Utilisateurs.class));
    }

    @Test
    public void testCreateUtilisateur_UserAlreadyExists() {
        // Arrange
        Utilisateurs newUtilisateur = new Utilisateurs();
        newUtilisateur.setEmail("test@example.com");
        newUtilisateur.setFkIdLaboratoire(1L);

        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(
                new LaboratoireDTO(1L, "Laboratoire Example", "logo", 12345L, true, LocalDate.now())); // Mock laboratoire exists
        when(utilisateursRepository.existsById("test@example.com")).thenReturn(true); // Mock user already exists

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            utilisateursService.createUtilisateur(newUtilisateur);
        });
        assertEquals("Utilisateur deja existant", exception.getMessage());
        verify(utilisateursRepository, never()).save(any(Utilisateurs.class));
    }
}
