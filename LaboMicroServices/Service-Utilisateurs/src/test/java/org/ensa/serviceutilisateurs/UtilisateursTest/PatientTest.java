package org.ensa.serviceutilisateurs.UtilisateursTest;

import org.ensa.serviceutilisateurs.controllers.PatientsRestController;
import org.ensa.serviceutilisateurs.entities.Patients;
import org.ensa.serviceutilisateurs.repositories.PatientRepository;
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

class PatientTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientsRestController patientsRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllPatients() {
        List<Patients> patients = Arrays.asList(new Patients(), new Patients());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patients> result = patientsRestController.getAllPatients();
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById_PatientExists() {
        Patients patient = new Patients();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        ResponseEntity<Patients> response = patientsRestController.getPatientById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPatientById_PatientDoesNotExist() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Patients> response = patientsRestController.getPatientById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testPatientExists_PatientExists() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = patientsRestController.patientExists(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(patientRepository, times(1)).existsById(1L);
    }

    @Test
    void testPatientExists_PatientDoesNotExist() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Boolean> response = patientsRestController.patientExists(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(patientRepository, times(1)).existsById(1L);
    }



    @Test
    void testDeletePatient_PatientExists() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = patientsRestController.deletePatient(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePatient_PatientDoesNotExist() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Boolean> response = patientsRestController.deletePatient(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientRepository, times(1)).existsById(1L);
        verify(patientRepository, never()).deleteById(1L);
    }
}
