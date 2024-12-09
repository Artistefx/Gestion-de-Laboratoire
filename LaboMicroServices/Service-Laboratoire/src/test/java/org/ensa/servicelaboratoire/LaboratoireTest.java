package org.ensa.servicelaboratoire;

import org.ensa.servicelaboratoire.controllers.LaboratoireRestController;
import org.ensa.servicelaboratoire.entities.Laboratoire;
import org.ensa.servicelaboratoire.repositories.LaboratoireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoireTest {

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private LaboratoireRestController laboratoireRestController;

    private Laboratoire laboratoire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        laboratoire = new Laboratoire();
        laboratoire.setId(1L);
        laboratoire.setNom("Laboratoire 1");
        laboratoire.setLogo("logo.png".getBytes());
        laboratoire.setNrc(10L);
        laboratoire.setActive(true);
        laboratoire.setDateActivation(LocalDate.parse("2024-01-01"));
    }

    @Test
    void testCreateLaboratoire() {
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        ResponseEntity<?> response = laboratoireRestController.createLaboratoire(laboratoire);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(laboratoire, response.getBody());
    }

    @Test
    void testCreateLaboratoire_Failure() {
        when(laboratoireRepository.save(any(Laboratoire.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = laboratoireRestController.createLaboratoire(laboratoire);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetAllLaboratoires() {
        when(laboratoireRepository.findAll()).thenReturn(List.of(laboratoire));

        ResponseEntity<List<Laboratoire>> response = laboratoireRestController.getAllLaboratoires();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetLaboratoireById() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.of(laboratoire));

        ResponseEntity<Laboratoire> response = laboratoireRestController.getLaboratoireById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(laboratoire, response.getBody());
    }

    @Test
    void testGetLaboratoireById_NotFound() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Laboratoire> response = laboratoireRestController.getLaboratoireById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateLaboratoire() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.of(laboratoire));
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        laboratoire.setNom("Updated Laboratoire");
        ResponseEntity<Laboratoire> response = laboratoireRestController.updateLaboratoire(1L, laboratoire);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Laboratoire", response.getBody().getNom());
    }

    @Test
    void testUpdateLaboratoire_NotFound() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Laboratoire> response = laboratoireRestController.updateLaboratoire(1L, laboratoire);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteLaboratoire() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.of(laboratoire));

        ResponseEntity<Boolean> response = laboratoireRestController.deleteLaboratoire(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteLaboratoire_NotFound() {
        when(laboratoireRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Boolean> response = laboratoireRestController.deleteLaboratoire(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
