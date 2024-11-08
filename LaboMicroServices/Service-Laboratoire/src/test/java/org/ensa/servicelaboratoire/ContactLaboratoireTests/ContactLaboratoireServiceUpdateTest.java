package org.ensa.servicelaboratoire.ContactLaboratoireTests;

import org.ensa.servicelaboratoire.entities.Adresse;
import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.entities.Laboratoire;
import org.ensa.servicelaboratoire.repositories.AdresseRepository;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.ensa.servicelaboratoire.repositories.LaboratoireRepository;
import org.ensa.servicelaboratoire.services.ContactLaboratoireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactLaboratoireServiceUpdateTest {

    @Mock
    private ContactLaboratoireRepository contactLaboratoireRepository;

    @Mock
    private AdresseRepository adresseRepository;

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private ContactLaboratoireService contactLaboratoireService;

    private ContactLaboratoire existingContact;
    private ContactLaboratoire updatedContact;
    private Adresse adresse;
    private Laboratoire laboratoire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        adresse = new Adresse();
        adresse.setId(1L);

        laboratoire = new Laboratoire();
        laboratoire.setId(1L);

        existingContact = new ContactLaboratoire();
        existingContact.setId(1L);
        existingContact.setAdresse(adresse);
        existingContact.setLaboratoire(laboratoire);
        existingContact.setNumTel(123456L);
        existingContact.setFax(654321L);
        existingContact.setEmail("existing@example.com");

        updatedContact = new ContactLaboratoire();
        updatedContact.setAdresse(adresse);
        updatedContact.setLaboratoire(laboratoire);
        updatedContact.setNumTel(789012L);
        updatedContact.setFax(210987L);
        updatedContact.setEmail("updated@example.com");
    }

    @Test
    void testUpdateContactLaboratoire_Success() {
        // Arrange
        when(contactLaboratoireRepository.findById(existingContact.getId())).thenReturn(Optional.of(existingContact));
        when(adresseRepository.existsById(adresse.getId())).thenReturn(true);
        when(laboratoireRepository.existsById(laboratoire.getId())).thenReturn(true);
        when(contactLaboratoireRepository.save(existingContact)).thenReturn(existingContact);

        // Act
        ContactLaboratoire result = contactLaboratoireService.updateContactLaboratoire(existingContact.getId(), updatedContact);

        // Assert
        assertNotNull(result);
        assertEquals(updatedContact.getNumTel(), result.getNumTel());
        assertEquals(updatedContact.getFax(), result.getFax());
        assertEquals(updatedContact.getEmail(), result.getEmail());
        verify(contactLaboratoireRepository, times(1)).save(existingContact);
    }

    @Test
    void testUpdateContactLaboratoire_AddressNotFound() {
        // Arrange
        when(contactLaboratoireRepository.findById(existingContact.getId())).thenReturn(Optional.of(existingContact));
        when(adresseRepository.existsById(adresse.getId())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.updateContactLaboratoire(existingContact.getId(), updatedContact)
        );

        assertEquals("Adresse n'existe pas", exception.getMessage());
        verify(contactLaboratoireRepository, never()).save(any(ContactLaboratoire.class));
    }

    @Test
    void testUpdateContactLaboratoire_LaboratoireNotFound() {
        // Arrange
        when(contactLaboratoireRepository.findById(existingContact.getId())).thenReturn(Optional.of(existingContact));
        when(adresseRepository.existsById(adresse.getId())).thenReturn(true);
        when(laboratoireRepository.existsById(laboratoire.getId())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.updateContactLaboratoire(existingContact.getId(), updatedContact)
        );

        assertEquals("Laboratoire n'existe pas", exception.getMessage());
        verify(contactLaboratoireRepository, never()).save(any(ContactLaboratoire.class));
    }

    @Test
    void testUpdateContactLaboratoire_ContactNotFound() {
        // Arrange
        when(contactLaboratoireRepository.findById(existingContact.getId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactLaboratoireService.updateContactLaboratoire(existingContact.getId(), updatedContact)
        );

        assertEquals("ContactLaboratoire n'existe pas avec cet ID", exception.getMessage());
        verify(contactLaboratoireRepository, never()).save(any(ContactLaboratoire.class));
    }
}
