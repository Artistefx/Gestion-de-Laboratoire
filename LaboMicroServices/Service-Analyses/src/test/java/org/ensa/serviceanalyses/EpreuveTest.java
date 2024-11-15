package org.ensa.serviceanalyses;

import org.ensa.serviceanalyses.controllers.EpreuveRestController;
import org.ensa.serviceanalyses.entities.Epreuve;
import org.ensa.serviceanalyses.repositories.EpreuveRepository;
import org.ensa.serviceanalyses.services.EpreuveService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EpreuveTest {

    @Mock
    private EpreuveRepository epreuveRepository;

    @Mock
    private EpreuveService epreuveService;

    @InjectMocks
    private EpreuveRestController epreuveRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEpreuves() {
        Epreuve epreuve1 = new Epreuve();
        epreuve1.setNom("Epreuve 1");

        Epreuve epreuve2 = new Epreuve();
        epreuve2.setNom("Epreuve 2");

        when(epreuveRepository.findAll()).thenReturn(Arrays.asList(epreuve1, epreuve2));

        List<Epreuve> result = epreuveRestController.getAllEpreuves();
        assertEquals(2, result.size());
        verify(epreuveRepository, times(1)).findAll();
    }

    @Test
    void testGetEpreuveById_found() {
        Epreuve epreuve = new Epreuve();
        epreuve.setId(1L);
        epreuve.setNom("Epreuve Found");

        when(epreuveRepository.findById(1L)).thenReturn(Optional.of(epreuve));

        ResponseEntity<Epreuve> response = epreuveRestController.getEpreuveById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(epreuve, response.getBody());
        verify(epreuveRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEpreuveById_notFound() {
        when(epreuveRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Epreuve> response = epreuveRestController.getEpreuveById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(epreuveRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateEpreuve_success() {
        Epreuve newEpreuve = new Epreuve();
        newEpreuve.setNom("New Epreuve");

        when(epreuveService.createEpreuve(any(Epreuve.class))).thenReturn(newEpreuve);

        ResponseEntity<?> response = epreuveRestController.createEpreuve(newEpreuve);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newEpreuve, response.getBody());
        verify(epreuveService, times(1)).createEpreuve(any(Epreuve.class));
    }

    @Test
    void testCreateEpreuve_error() {
        Epreuve newEpreuve = new Epreuve();
        newEpreuve.setNom("Invalid Epreuve");

        when(epreuveService.createEpreuve(any(Epreuve.class))).thenThrow(new IllegalArgumentException("Analyse non trouvée"));

        ResponseEntity<?> response = epreuveRestController.createEpreuve(newEpreuve);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Analyse non trouvée", response.getBody());
        verify(epreuveService, times(1)).createEpreuve(any(Epreuve.class));
    }

    @Test
    void testUpdateEpreuve_success() {
        Epreuve updatedEpreuve = new Epreuve();
        updatedEpreuve.setNom("Updated Epreuve");

        when(epreuveService.updateEpreuve(eq(1L), any(Epreuve.class))).thenReturn(updatedEpreuve);

        ResponseEntity<?> response = epreuveRestController.updateEpreuve(1L, updatedEpreuve);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEpreuve, response.getBody());
        verify(epreuveService, times(1)).updateEpreuve(eq(1L), any(Epreuve.class));
    }

    @Test
    void testUpdateEpreuve_notFound() {
        Epreuve updatedEpreuve = new Epreuve();
        updatedEpreuve.setNom("Updated Epreuve");

        when(epreuveService.updateEpreuve(eq(1L), any(Epreuve.class))).thenThrow(new IllegalArgumentException("Épreuve non trouvée"));

        ResponseEntity<?> response = epreuveRestController.updateEpreuve(1L, updatedEpreuve);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Épreuve non trouvée", response.getBody());
        verify(epreuveService, times(1)).updateEpreuve(eq(1L), any(Epreuve.class));
    }

    @Test
    void testDeleteEpreuve_success() {
        when(epreuveRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = epreuveRestController.deleteEpreuve(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(epreuveRepository, times(1)).existsById(1L);
        verify(epreuveRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEpreuve_notFound() {
        when(epreuveRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = epreuveRestController.deleteEpreuve(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(epreuveRepository, times(1)).existsById(1L);
        verify(epreuveRepository, times(0)).deleteById(anyLong());
    }
}
