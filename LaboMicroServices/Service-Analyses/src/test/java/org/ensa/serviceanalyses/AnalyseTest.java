package org.ensa.serviceanalyses;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.ensa.serviceanalyses.controllers.AnalyseRestController;
import org.ensa.serviceanalyses.entities.Analyse;
import org.ensa.serviceanalyses.repositories.AnalyseRepository;
import org.ensa.serviceanalyses.services.AnalyseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnalyseTest {

    @Mock
    private AnalyseRepository analyseRepository;

    @Mock
    private AnalyseService analyseService;

    @InjectMocks
    private AnalyseRestController analyseRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAnalyse_Success() {
        Analyse analyse = new Analyse();
        when(analyseService.createAnalyse(any(Analyse.class))).thenReturn(analyse);

        ResponseEntity<?> response = analyseRestController.createAnalyse(analyse);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(analyse, response.getBody());
    }

    @Test
    void testCreateAnalyse_BadRequest() {
        Analyse analyse = new Analyse();
        when(analyseService.createAnalyse(any(Analyse.class)))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<?> response = analyseRestController.createAnalyse(analyse);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody());
    }

    @Test
    void testCreateAnalyse_InternalError() {
        Analyse analyse = new Analyse();
        when(analyseService.createAnalyse(any(Analyse.class)))
                .thenThrow(new RuntimeException("Unknown error"));

        ResponseEntity<?> response = analyseRestController.createAnalyse(analyse);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erreur inconnue", response.getBody());
    }

    @Test
    void testGetAllAnalyses() {
        List<Analyse> analyses = new ArrayList<>();
        analyses.add(new Analyse());
        when(analyseRepository.findAll()).thenReturn(analyses);

        List<Analyse> result = analyseRestController.getAllAnalyses();

        assertEquals(1, result.size());
        verify(analyseRepository, times(1)).findAll();
    }

    @Test
    void testGetAnalyseById_Found() {
        Analyse analyse = new Analyse();
        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));

        ResponseEntity<Analyse> response = analyseRestController.getAnalyseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(analyse, response.getBody());
    }

    @Test
    void testGetAnalyseById_NotFound() {
        when(analyseRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Analyse> response = analyseRestController.getAnalyseById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateAnalyse_Success() {
        Analyse updatedAnalyse = new Analyse();
        Analyse savedAnalyse = new Analyse();
        when(analyseService.updateAnalyse(eq(1L), any(Analyse.class))).thenReturn(savedAnalyse);

        ResponseEntity<?> response = analyseRestController.updateAnalyse(1L, updatedAnalyse);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedAnalyse, response.getBody());
    }

    @Test
    void testUpdateAnalyse_BadRequest() {
        Analyse updatedAnalyse = new Analyse();
        when(analyseService.updateAnalyse(eq(1L), any(Analyse.class)))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<?> response = analyseRestController.updateAnalyse(1L, updatedAnalyse);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody());
    }


    @Test
    void testUpdateAnalyse_InternalError() {
        Analyse updatedAnalyse = new Analyse();
        when(analyseService.updateAnalyse(eq(1L), any(Analyse.class)))
                .thenThrow(new RuntimeException("Unknown error"));

        ResponseEntity<?> response = analyseRestController.updateAnalyse(1L, updatedAnalyse);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erreur: Une erreur est survenue lors de la mise a jour de l'analyse.", response.getBody());
    }

    @Test
    void testDeleteAnalyse_Success() {
        when(analyseRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = analyseRestController.deleteAnalyse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(analyseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAnalyse_NotFound() {
        when(analyseRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Boolean> response = analyseRestController.deleteAnalyse(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(analyseRepository, never()).deleteById(anyLong());
    }
}
