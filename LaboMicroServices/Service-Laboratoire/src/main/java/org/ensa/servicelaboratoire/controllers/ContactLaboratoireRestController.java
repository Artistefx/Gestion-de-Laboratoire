package org.ensa.servicelaboratoire.controllers;



import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contactLaboratoire")
public class ContactLaboratoireRestController {

    @Autowired
    private ContactLaboratoireRepository contactLaboratoireRepository;

    public ContactLaboratoireRestController(ContactLaboratoireRepository contactLaboratoireRepository) {
        this.contactLaboratoireRepository = contactLaboratoireRepository;
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
    public ContactLaboratoire createContact(@RequestBody ContactLaboratoire contact) {
        return contactLaboratoireRepository.save(contact);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ContactLaboratoire> updateContact(@PathVariable Long id, @RequestBody ContactLaboratoire updatedContact) {
        return contactLaboratoireRepository.findById(id).map(existingContact -> {
            existingContact.setLaboratoire(updatedContact.getLaboratoire());
            existingContact.setAdresse(updatedContact.getAdresse());
            existingContact.setNumTel(updatedContact.getNumTel());
            existingContact.setFax(updatedContact.getFax());
            existingContact.setEmail(updatedContact.getEmail());
            ContactLaboratoire savedContact = contactLaboratoireRepository.save(existingContact);
            return ResponseEntity.ok(savedContact);
        }).orElseGet(() -> ResponseEntity.notFound().build());
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

