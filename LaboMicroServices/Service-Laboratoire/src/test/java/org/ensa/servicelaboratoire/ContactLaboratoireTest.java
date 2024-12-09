package org.ensa.servicelaboratoire;

import org.ensa.servicelaboratoire.controllers.ContactLaboratoireRestController;
import org.ensa.servicelaboratoire.entities.Adresse;
import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.entities.Laboratoire;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.ensa.servicelaboratoire.services.ContactLaboratoireService;
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

class ContactLaboratoireTest {

    @Mock
    private ContactLaboratoireRepository contactLaboratoireRepository;

    @Mock
    private ContactLaboratoireService contactLaboratoireService;

    @InjectMocks
    private ContactLaboratoireRestController contactLaboratoireRestController;

    private ContactLaboratoire contact;
    private Laboratoire laboratoire;
    private Adresse adresse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating a sample contact for testing
        contact = new ContactLaboratoire();
        laboratoire = new Laboratoire();
        adresse = new Adresse();
        contact.setId(1L);
        contact.setNumTel(1234567890L);
        contact.setEmail("johndoe@example.com");
        contact.setFax(1234567890L);
        contact.setLaboratoire(laboratoire);
        contact.setAdresse(adresse);
    }

    @Test
    void testCreateContact() {
        when(contactLaboratoireService.createContactLaboratoire(any(ContactLaboratoire.class))).thenReturn(contact);

        ResponseEntity<?> response = contactLaboratoireRestController.createContact(contact);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(contact, response.getBody());
    }

    @Test
    void testCreateContact_BadRequest() {
        when(contactLaboratoireService.createContactLaboratoire(any(ContactLaboratoire.class)))
                .thenThrow(new IllegalArgumentException("Invalid Contact"));

        ResponseEntity<?> response = contactLaboratoireRestController.createContact(contact);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Contact", response.getBody());
    }

    @Test
    void testGetAllContacts() {
        when(contactLaboratoireRepository.findAll()).thenReturn(List.of(contact));

        List<ContactLaboratoire> response = contactLaboratoireRestController.getAllContacts();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(contact, response.get(0));
    }

    @Test
    void testGetContactById() {
        when(contactLaboratoireRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        ResponseEntity<ContactLaboratoire> response = contactLaboratoireRestController.getContactById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
    }

    @Test
    void testGetContactById_NotFound() {
        when(contactLaboratoireRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ContactLaboratoire> response = contactLaboratoireRestController.getContactById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateContact() {
        ContactLaboratoire updatedContact = new ContactLaboratoire();
        laboratoire = new Laboratoire();
        adresse = new Adresse();
        contact.setId(1L);
        updatedContact.setNumTel(987654L);
        updatedContact.setEmail("johndoe@example.com");
        updatedContact.setFax(987654L);
        updatedContact.setLaboratoire(laboratoire);
        updatedContact.setAdresse(adresse);


        when(contactLaboratoireRepository.findById(anyLong())).thenReturn(Optional.of(contact));
        when(contactLaboratoireService.updateContactLaboratoire(anyLong(), any(ContactLaboratoire.class))).thenReturn(updatedContact);

        ResponseEntity<?> response = contactLaboratoireRestController.updateContact(1L, updatedContact);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedContact, response.getBody());
    }

    @Test
    void testUpdateContact_NotFound() {
        ContactLaboratoire updatedContact = new ContactLaboratoire();
        updatedContact.setEmail("test@email.com");

        when(contactLaboratoireService.updateContactLaboratoire(anyLong() , any(ContactLaboratoire.class))).thenThrow( new IllegalArgumentException("ContactLaboratoire n'existe pas avec cet ID"));

        ResponseEntity<?> response = contactLaboratoireRestController.updateContact(1L, updatedContact);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteContact() {
        when(contactLaboratoireRepository.existsById(anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = contactLaboratoireRestController.deleteContact(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(contactLaboratoireRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteContact_NotFound() {
        when(contactLaboratoireRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<Boolean> response = contactLaboratoireRestController.deleteContact(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
