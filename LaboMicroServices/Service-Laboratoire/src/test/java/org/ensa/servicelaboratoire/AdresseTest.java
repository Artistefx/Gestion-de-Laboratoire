package org.ensa.servicelaboratoire;

import org.ensa.servicelaboratoire.controllers.AdresseRestController;
import org.ensa.servicelaboratoire.entities.Adresse;
import org.ensa.servicelaboratoire.repositories.AdresseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class AdresseTest {

    @Mock
    private AdresseRepository adresseRepository;

    @InjectMocks
    private AdresseRestController adresseRestController;

    private Adresse adresse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un objet Adresse pour les tests
        adresse = new Adresse();
        adresse.setId(1L);
        adresse.setNumVoie(10L);
        adresse.setNomVoie("Rue Example");
        adresse.setCodePostal(75000L);
        adresse.setVille("Paris");
        adresse.setCommmune("Paris");
    }

    @Test
    void testCreateAdresse() {
        when(adresseRepository.save(any(Adresse.class))).thenReturn(adresse);

        Adresse response = adresseRestController.createAdresse(adresse);

        assertNotNull(response);
        assertEquals(adresse.getNumVoie(), response.getNumVoie());
        assertEquals(adresse.getNomVoie(), response.getNomVoie());
    }

    @Test
    void testGetAllAdresses() {
        when(adresseRepository.findAll()).thenReturn(List.of(adresse));

        List<Adresse> response = adresseRestController.getAllAdresses();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(adresse, response.get(0));
    }

    @Test
    void testGetAdresseById() {
        when(adresseRepository.findById(anyLong())).thenReturn(Optional.of(adresse));

        ResponseEntity<Adresse> response = adresseRestController.getAdresseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adresse, response.getBody());
    }

    @Test
    void testGetAdresseById_NotFound() {
        when(adresseRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Adresse> response = adresseRestController.getAdresseById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateAdresse() {
        Adresse updatedAdresse = new Adresse();
        updatedAdresse.setNumVoie(20L);
        updatedAdresse.setNomVoie("Rue Updated");
        updatedAdresse.setCodePostal(75001L);
        updatedAdresse.setVille("Paris");
        updatedAdresse.setCommmune("Paris");

        when(adresseRepository.findById(anyLong())).thenReturn(Optional.of(adresse));
        when(adresseRepository.save(any(Adresse.class))).thenReturn(updatedAdresse);

        ResponseEntity<Adresse> response = adresseRestController.updateAdresse(1L, updatedAdresse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAdresse.getNumVoie(), response.getBody().getNumVoie());
        assertEquals(updatedAdresse.getNomVoie(), response.getBody().getNomVoie());
    }

    @Test
    void testUpdateAdresse_NotFound() {
        when(adresseRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Adresse> response = adresseRestController.updateAdresse(1L, adresse);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteAdresse() {
        when(adresseRepository.existsById(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = adresseRestController.deleteAdresse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteAdresse_NotFound() {
        when(adresseRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = adresseRestController.deleteAdresse(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
