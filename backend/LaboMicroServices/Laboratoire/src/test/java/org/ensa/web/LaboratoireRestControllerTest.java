package org.ensa.web;

import org.ensa.entities.laboratoire;
import org.ensa.repository.LaboratoireRepository;
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
import static org.mockito.Mockito.*;



public class LaboratoireRestControllerTest {

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private LaboratoireRestController laboratoireRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLaboratoire() {
        laboratoire lab = new laboratoire(null, "Laboratoire A", new byte[0], 123456L, true);
        when(laboratoireRepository.save(lab)).thenReturn(lab);

        ResponseEntity<laboratoire> response = laboratoireRestController.createLaboratoire(lab);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laboratoire A", response.getBody().getNom());
        verify(laboratoireRepository, times(1)).save(lab);
    }
    @Test
    void testGetAllLaboratoires() {
        List<laboratoire> laboratoires = new ArrayList<>();
        laboratoires.add(new laboratoire(1L, "Laboratoire A", new byte[0], 123456L, true));
        laboratoires.add(new laboratoire(2L, "Laboratoire B", new byte[0], 789012L, false));
        when(laboratoireRepository.findAll()).thenReturn(laboratoires);

        ResponseEntity<List<laboratoire>> response = laboratoireRestController.getAllLaboratoires();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(laboratoireRepository, times(1)).findAll();
    }
    @Test
    void testGetLaboratoireById() {
        laboratoire lab = new laboratoire(1L, "Laboratoire A", new byte[0], 123456L, true);
        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(lab));

        ResponseEntity<laboratoire> response = laboratoireRestController.getLaboratoireById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laboratoire A", response.getBody().getNom());
        verify(laboratoireRepository, times(1)).findById(1L);
    }
    @Test
    void testUpdateLaboratoire() {
        laboratoire existingLab = new laboratoire(1L, "Laboratoire A", new byte[0], 123456L, true);
        laboratoire updatedLab = new laboratoire(null, "Laboratoire B", new byte[0], 654321L, false);
        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(existingLab));
        when(laboratoireRepository.save(existingLab)).thenReturn(existingLab);

        ResponseEntity<laboratoire> response = laboratoireRestController.updateLaboratoire(1L, updatedLab);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laboratoire B", response.getBody().getNom());
        assertEquals(654321L, response.getBody().getNrc());
        assertEquals(false, response.getBody().isActive());
        verify(laboratoireRepository, times(1)).findById(1L);
        verify(laboratoireRepository, times(1)).save(existingLab);
    }


}

