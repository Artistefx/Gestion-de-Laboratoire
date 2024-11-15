package org.ensa.servicelaboratoire.controllers;



import org.ensa.servicelaboratoire.entities.Laboratoire;
import org.ensa.servicelaboratoire.repositories.LaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/laboratoire")
public class LaboratoireRestController {

    private final LaboratoireRepository laboratoireRepository;

    public LaboratoireRestController(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }


    @PostMapping
    public ResponseEntity<?> createLaboratoire(@RequestBody Laboratoire Laboratoire) {
        try {
            Laboratoire savedLaboratoire = laboratoireRepository.save(Laboratoire);
            return new ResponseEntity<>(savedLaboratoire, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(laboratoireRepository.existsById(id));
    }


    @GetMapping
    public ResponseEntity<List<Laboratoire>> getAllLaboratoires() {
        List<Laboratoire> laboratoires = laboratoireRepository.findAll();
        return new ResponseEntity<>(laboratoires, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Laboratoire> getLaboratoireById(@PathVariable("id") Long id) {
        Optional<Laboratoire> laboratoire = laboratoireRepository.findById(id);
        return laboratoire.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Laboratoire> updateLaboratoire(@PathVariable("id") Long id, @RequestBody Laboratoire laboratoireDetails) {
        Optional<Laboratoire> laboratoireData = laboratoireRepository.findById(id);

        if (laboratoireData.isPresent()) {
            Laboratoire laboratoire = laboratoireData.get();
            laboratoire.setNom(laboratoireDetails.getNom());
            laboratoire.setLogo(laboratoireDetails.getLogo());
            laboratoire.setNrc(laboratoireDetails.getNrc());
            laboratoire.setActive(laboratoireDetails.isActive());
            laboratoire.setDateActivation(laboratoireDetails.getDateActivation());

            return new ResponseEntity<>(laboratoireRepository.save(laboratoire), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Laboratoire> deleteLaboratoire(@PathVariable("id") Long id) {
        Optional<Laboratoire> laboratoire = laboratoireRepository.findById(id);
        if (laboratoire.isPresent()) {
            laboratoireRepository.delete(laboratoire.get());
            return new ResponseEntity<>(laboratoire.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
