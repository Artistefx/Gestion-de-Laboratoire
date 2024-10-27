package org.ensa.conactlaboratoire.web;

import org.ensa.conactlaboratoire.entities.contactLaboratoire;
import org.ensa.conactlaboratoire.repository.contactLaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contactLaboratoire")
public class contactLaboratoireRestController {

    @Autowired
    private contactLaboratoireRepository contactLaboratoireRepository;

    public contactLaboratoireRestController(contactLaboratoireRepository contactLaboratoireRepository) {
        this.contactLaboratoireRepository = contactLaboratoireRepository;
    }


    @GetMapping
    public List<contactLaboratoire> getAllContacts() {
        return contactLaboratoireRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<contactLaboratoire> getContactById(@PathVariable Long id) {
        Optional<contactLaboratoire> contact = contactLaboratoireRepository.findById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public contactLaboratoire createContact(@RequestBody contactLaboratoire contact) {
        return contactLaboratoireRepository.save(contact);
    }


    @PutMapping("/{id}")
    public ResponseEntity<contactLaboratoire> updateContact(@PathVariable Long id, @RequestBody contactLaboratoire updatedContact) {
        return contactLaboratoireRepository.findById(id).map(existingContact -> {
            existingContact.setFkIdLaboratoire(updatedContact.getFkIdLaboratoire());
            existingContact.setFkIdAdresse(updatedContact.getFkIdAdresse());
            existingContact.setNumTel(updatedContact.getNumTel());
            existingContact.setFax(updatedContact.getFax());
            existingContact.setEmail(updatedContact.getEmail());
            contactLaboratoire savedContact = contactLaboratoireRepository.save(existingContact);
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
