package org.ensa.serviceanalyses;

import org.ensa.serviceanalyses.controllers.TestAnalyseRestController;
import org.ensa.serviceanalyses.entities.TestAnalyse;
import org.ensa.serviceanalyses.repositories.TestAnalyseRepository;
import org.ensa.serviceanalyses.services.TestAnalyseService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestAnalyseTest {

    @Mock
    private TestAnalyseRepository testAnalyseRepository;

    @Mock
    private TestAnalyseService testAnalyseService;

    @InjectMocks
    private TestAnalyseRestController testAnalyseRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTestAnalyses() {
        List<TestAnalyse> testAnalyses = new ArrayList<>();
        testAnalyses.add(new TestAnalyse());
        when(testAnalyseRepository.findAll()).thenReturn(testAnalyses);

        List<TestAnalyse> result = testAnalyseRestController.testAnalyseList();

        assertEquals(1, result.size());
        verify(testAnalyseRepository, times(1)).findAll();
    }

    @Test
    void testGetTestAnalyseById_Found() {
        TestAnalyse testAnalyse = new TestAnalyse();
        when(testAnalyseRepository.findById(1L)).thenReturn(Optional.of(testAnalyse));

        ResponseEntity<TestAnalyse> response = testAnalyseRestController.getTestAnalyseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAnalyse, response.getBody());
    }

    @Test
    void testGetTestAnalyseById_NotFound() {
        when(testAnalyseRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<TestAnalyse> response = testAnalyseRestController.getTestAnalyseById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    void testAddTestAnalyse_Success() {
        TestAnalyse testAnalyse = new TestAnalyse();
        when(testAnalyseService.createTestAnalyse(any(TestAnalyse.class))).thenReturn(testAnalyse);

        ResponseEntity<?> response = testAnalyseRestController.addTestAnalyse(testAnalyse);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testAnalyse, response.getBody());
    }

    @Test
    void testAddTestAnalyse_AnalyseNotFound() {
        TestAnalyse testAnalyse = new TestAnalyse();
        when(testAnalyseService.createTestAnalyse(any(TestAnalyse.class))).thenThrow(new IllegalArgumentException("Analyse not found for given ID"));

        ResponseEntity<?> response = testAnalyseRestController.addTestAnalyse(testAnalyse);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Analyse not found for given ID", response.getBody());
    }

    @Test
    void testUpdateTestAnalyse_Success() {
        TestAnalyse updatedTestAnalyse = new TestAnalyse();
        when(testAnalyseService.updateTestAnalyse(eq(1L), any(TestAnalyse.class))).thenReturn(updatedTestAnalyse);

        ResponseEntity<?> response = testAnalyseRestController.updateTestAnalyse(1L, updatedTestAnalyse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTestAnalyse, response.getBody());
    }

    @Test
    void testUpdateTestAnalyse_NotFound() {
        TestAnalyse updatedTestAnalyse = new TestAnalyse();
        when(testAnalyseService.updateTestAnalyse(eq(1L), any(TestAnalyse.class)))
                .thenThrow(new IllegalArgumentException("TestAnalyse not found for given ID"));

        ResponseEntity<?> response = testAnalyseRestController.updateTestAnalyse(1L, updatedTestAnalyse);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("TestAnalyse not found for given ID", response.getBody());
    }

    @Test
    void testDeleteTestAnalyse_Success() {
        when(testAnalyseRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = testAnalyseRestController.deleteTestAnalyse(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(testAnalyseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTestAnalyse_NotFound() {
        when(testAnalyseRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = testAnalyseRestController.deleteTestAnalyse(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(testAnalyseRepository, never()).deleteById(anyLong());
    }
}
