package org.ensa.servicelaboratoire.services;

import jakarta.transaction.Transactional;
import org.ensa.servicelaboratoire.entities.ContactLaboratoire;
import org.ensa.servicelaboratoire.repositories.AdresseRepository;
import org.ensa.servicelaboratoire.repositories.ContactLaboratoireRepository;
import org.ensa.servicelaboratoire.repositories.LaboratoireRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactLaboratoireService {

    private final ContactLaboratoireRepository contactLaboratoireRepository;
    private final AdresseRepository adresseRepository;
    private final LaboratoireRepository laboratoireRepository;

    public ContactLaboratoireService(ContactLaboratoireRepository contactLaboratoireRepository,
                                     AdresseRepository adresseRepository, LaboratoireRepository laboratoireRepository) {
        this.contactLaboratoireRepository = contactLaboratoireRepository;
        this.adresseRepository = adresseRepository;
        this.laboratoireRepository = laboratoireRepository;
    }

    @Transactional
    public ContactLaboratoire createContactLaboratoire(ContactLaboratoire contactLaboratoire) {

        if (!adresseRepository.existsById(contactLaboratoire.getAdresse().getId())) {
            throw new IllegalArgumentException("Adresse n'existe pas");
        }

        if (!laboratoireRepository.existsById(contactLaboratoire.getLaboratoire().getId())) {
            throw new IllegalArgumentException("Laboratoire n'existe pas");
        }

        Optional<ContactLaboratoire> existingContact = contactLaboratoireRepository.findByAdresseId(contactLaboratoire.getAdresse().getId());
        if (existingContact.isPresent() && !existingContact.get().getId().equals(contactLaboratoire.getId())) {
            throw new IllegalArgumentException("Adresse est déjà assignée à un autre contact");
        }

        return contactLaboratoireRepository.save(contactLaboratoire);
    }

    @Transactional
    public ContactLaboratoire updateContactLaboratoire(Long id, ContactLaboratoire updatedContact) {
        return contactLaboratoireRepository.findById(id).map(existingContact -> {
            if (!adresseRepository.existsById(updatedContact.getAdresse().getId())) {
                throw new IllegalArgumentException("Adresse n'existe pas");
            }

            if (!laboratoireRepository.existsById(updatedContact.getLaboratoire().getId())) {
                throw new IllegalArgumentException("Laboratoire n'existe pas");
            }

            existingContact.setLaboratoire(updatedContact.getLaboratoire());
            existingContact.setAdresse(updatedContact.getAdresse());
            existingContact.setNumTel(updatedContact.getNumTel());
            existingContact.setFax(updatedContact.getFax());
            existingContact.setEmail(updatedContact.getEmail());

            return contactLaboratoireRepository.save(existingContact);
        }).orElseThrow(() -> new IllegalArgumentException("ContactLaboratoire n'existe pas avec cet ID"));
    }




}
