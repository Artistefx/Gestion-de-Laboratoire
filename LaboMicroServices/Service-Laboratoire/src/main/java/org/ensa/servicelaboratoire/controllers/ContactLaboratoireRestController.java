package org.ensa.servicelaboratoire.controllers;



import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.ensa.servicelaboratoire.services.ContactLaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contactLaboratoire")
public class ContactLaboratoireRestController {

    private final ContactLaboratoireRepository contactLaboratoireRepository;
    private final ContactLaboratoireService contactLaboratoireService;

    public ContactLaboratoireRestController(ContactLaboratoireRepository contactLaboratoireRepository,
                                            ContactLaboratoireService contactLaboratoireService) {
        this.contactLaboratoireRepository = contactLaboratoireRepository;
        this.contactLaboratoireService = contactLaboratoireService;
    }


    @GetMapping
    public List<ContactLaboratoire> getAllContacts() {
        return contactLaboratoireRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContactLaboratoire> getContactById(@PathVariable Long id) {
        Optional<ContactLaboratoire> contact = contactLaboratoireRepository.findById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactLaboratoire contact) {
        try {
            ContactLaboratoire savedContact = contactLaboratoireService.createContactLaboratoire(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur: Une erreur est survenue lors de la création du contact.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody ContactLaboratoire updatedContact) {
        try {
            ContactLaboratoire savedContact = contactLaboratoireService.updateContactLaboratoire(id, updatedContact);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur: Une erreur est survenue lors de la création du contact.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactLaboratoireRepository.existsById(id)) {
            contactLaboratoireRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

