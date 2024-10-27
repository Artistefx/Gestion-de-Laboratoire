package org.ensa.web;

import org.ensa.entities.laboratoire;
import org.ensa.repository.LaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/laboratoires")
public class LaboratoireRestController {

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    public LaboratoireRestController(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }


    @PostMapping
    public ResponseEntity<laboratoire> createLaboratoire(@RequestBody laboratoire laboratoire) {
        try {
            laboratoire savedLaboratoire = laboratoireRepository.save(laboratoire);
            return new ResponseEntity<>(savedLaboratoire, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<laboratoire>> getAllLaboratoires() {
        List<laboratoire> laboratoires = laboratoireRepository.findAll();
        return new ResponseEntity<>(laboratoires, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<laboratoire> getLaboratoireById(@PathVariable("id") Long id) {
        Optional<laboratoire> laboratoire = laboratoireRepository.findById(id);
        return laboratoire.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<laboratoire> updateLaboratoire(@PathVariable("id") Long id, @RequestBody laboratoire laboratoireDetails) {
        Optional<laboratoire> laboratoireData = laboratoireRepository.findById(id);

        if (laboratoireData.isPresent()) {
            laboratoire laboratoire = laboratoireData.get();
            laboratoire.setNom(laboratoireDetails.getNom());
            laboratoire.setLogo(laboratoireDetails.getLogo());
            laboratoire.setNrc(laboratoireDetails.getNrc());
            laboratoire.setActive(laboratoireDetails.isActive());
            return new ResponseEntity<>(laboratoireRepository.save(laboratoire), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    }
