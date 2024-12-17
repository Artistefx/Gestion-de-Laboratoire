package org.ensa.serviceexamination;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ensa.serviceexamination.controllers.DossierRestController;
import org.ensa.serviceexamination.entities.Dossier;
import org.ensa.serviceexamination.repositories.DossierRepository;
import org.ensa.serviceexamination.services.DossierService;
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

class DossierTest {

    @Mock
    private DossierRepository dossierRepository;

    @Mock
    private DossierService dossierService;

    @InjectMocks
    private DossierRestController dossierRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDossier() throws JsonProcessingException {
        Dossier dossier = new Dossier();
        when(dossierService.CreateOrUpdateDossier(any(Dossier.class))).thenReturn(dossier);

        ResponseEntity<?> response = dossierRestController.createDossier(dossier);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dossier, response.getBody());
        verify(dossierService, times(1)).CreateOrUpdateDossier(dossier);
    }

    @Test
    void testGetAllDossiers() {
        List<Dossier> dossiers = Arrays.asList(new Dossier(), new Dossier());
        when(dossierRepository.findAll()).thenReturn(dossiers);

        List<Dossier> result = dossierRestController.getAllDossiers();
        assertEquals(2, result.size());
        verify(dossierRepository, times(1)).findAll();
    }

    @Test
    void testGetDossierById_DossierExists() {
        Dossier dossier = new Dossier();
        when(dossierRepository.findById(1L)).thenReturn(Optional.of(dossier));

        ResponseEntity<Dossier> response = dossierRestController.getDossierById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dossier, response.getBody());
        verify(dossierRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDossierById_DossierDoesNotExist() {
        when(dossierRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Dossier> response = dossierRestController.getDossierById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(dossierRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDossierByIdUtilisateur() {
        List<Dossier> dossiers = Arrays.asList(new Dossier(), new Dossier());
        when(dossierRepository.findAllByFkEmailUtilisateur("user@example.com")).thenReturn(dossiers);

        List<Dossier> result = dossierRestController.getDossierByIdUtilistateur("user@example.com");
        assertEquals(2, result.size());
        verify(dossierRepository, times(1)).findAllByFkEmailUtilisateur("user@example.com");
    }

    @Test
    void testGetDossierByIdPatient() {
        List<Dossier> dossiers = Arrays.asList(new Dossier(), new Dossier());
        when(dossierRepository.findAllByFkIdPatient(1L)).thenReturn(dossiers);

        List<Dossier> result = dossierRestController.getDossierByIdPassion(1L);
        assertEquals(2, result.size());
        verify(dossierRepository, times(1)).findAllByFkIdPatient(1L);
    }

    @Test
    void testUpdateDossier_DossierExists() throws JsonProcessingException {
        Dossier existingDossier = new Dossier();
        Dossier updatedDossier = new Dossier();
        updatedDossier.setFkEmailUtilisateur("updated@example.com");

        when(dossierRepository.findById(1L)).thenReturn(Optional.of(existingDossier));
        when(dossierService.CreateOrUpdateDossier(any(Dossier.class))).thenReturn(existingDossier);

        ResponseEntity<?> response = dossierRestController.updateDossier(1L, updatedDossier);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingDossier, response.getBody());
        verify(dossierRepository, times(1)).findById(1L);
        verify(dossierService, times(1)).CreateOrUpdateDossier(existingDossier);
    }

    @Test
    void testUpdateDossier_DossierDoesNotExist() throws JsonProcessingException {
        Dossier updatedDossier = new Dossier();
        when(dossierRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = dossierRestController.updateDossier(1L, updatedDossier);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(dossierRepository, times(1)).findById(1L);
        verify(dossierService, never()).CreateOrUpdateDossier(any(Dossier.class));
    }

    @Test
    void testDeleteDossier_DossierExists() {
        when(dossierRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = dossierRestController.deleteDossier(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dossierRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDossier_DossierDoesNotExist() {
        when(dossierRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = dossierRestController.deleteDossier(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(dossierRepository, times(1)).existsById(1L);
        verify(dossierRepository, never()).deleteById(1L);
    }
}
