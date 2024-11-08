package org.ensa.servicelaboratoire.ContactLaboratoireTests;

import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.entities.Laboratoire;
import org.ensa.servicelaboratoire.entities.Adresse;
import org.ensa.servicelaboratoire.repositories.AdresseRepository;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.ensa.servicelaboratoire.repositories.LaboratoireRepository;
import org.ensa.servicelaboratoire.services.ContactLaboratoireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContactLaboratoireServiceCreateTest {

    @Mock
    private ContactLaboratoireRepository contactLaboratoireRepository;

    @Mock
    private AdresseRepository adresseRepository;

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private ContactLaboratoireService contactLaboratoireService;

    private ContactLaboratoire validContactLaboratoire;
    private Adresse validAdresse;
    private Laboratoire validLaboratoire;

    @BeforeEach
    public void setUp() {
        // Setup mock data for a valid ContactLaboratoire
        validAdresse = new Adresse();
        validAdresse.setId(1L);

        validLaboratoire = new Laboratoire();
        validLaboratoire.setId(1L);

        validContactLaboratoire = new ContactLaboratoire();
        validContactLaboratoire.setId(1L);
        validContactLaboratoire.setAdresse(validAdresse);
        validContactLaboratoire.setLaboratoire(validLaboratoire);
    }

    @Test
    public void testCreateOrUpdateContactLaboratoire_success() {

        when(adresseRepository.existsById(validAdresse.getId())).thenReturn(true);
        when(laboratoireRepository.existsById(validLaboratoire.getId())).thenReturn(true);
        when(contactLaboratoireRepository.findByAdresseId(validAdresse.getId())).thenReturn(Optional.empty());
        when(contactLaboratoireRepository.save(validContactLaboratoire)).thenReturn(validContactLaboratoire);

        ContactLaboratoire createdContact = contactLaboratoireService.createContactLaboratoire(validContactLaboratoire);

        assertNotNull(createdContact);
        assertEquals(validContactLaboratoire.getId(), createdContact.getId());
        verify(contactLaboratoireRepository, times(1)).save(validContactLaboratoire);
    }

    @Test
    public void testCreateOrUpdateContactLaboratoire_addressNotFound() {
        // Arrange
        when(adresseRepository.existsById(validAdresse.getId())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.createContactLaboratoire(validContactLaboratoire)
        );
        assertEquals("Adresse n'existe pas", exception.getMessage());
    }

    @Test
    public void testCreateOrUpdateContactLaboratoire_laboratoireNotFound() {
        // Arrange
        when(adresseRepository.existsById(validAdresse.getId())).thenReturn(true);
        when(laboratoireRepository.existsById(validLaboratoire.getId())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.createContactLaboratoire(validContactLaboratoire)
        );
        assertEquals("Laboratoire n'existe pas", exception.getMessage());
    }

    @Test
    public void testCreateOrUpdateContactLaboratoire_addressAlreadyAssigned() {
        // Arrange
        when(adresseRepository.existsById(validAdresse.getId())).thenReturn(true);
        when(laboratoireRepository.existsById(validLaboratoire.getId())).thenReturn(true);
        ContactLaboratoire existingContact = new ContactLaboratoire();
        existingContact.setId(2L);
        existingContact.setAdresse(validAdresse);
        when(contactLaboratoireRepository.findByAdresseId(validAdresse.getId())).thenReturn(Optional.of(existingContact));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.createContactLaboratoire(validContactLaboratoire)
        );
        assertEquals("Adresse est déjà assignée à un autre contact", exception.getMessage());
    }
}
